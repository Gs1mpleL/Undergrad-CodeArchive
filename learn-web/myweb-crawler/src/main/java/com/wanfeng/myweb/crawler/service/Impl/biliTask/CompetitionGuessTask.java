package com.wanfeng.myweb.crawler.service.Impl.biliTask;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wanfeng.myweb.api.client.UserClient;
import com.wanfeng.myweb.common.Utils.ThreadLocalUtils;
import com.wanfeng.myweb.crawler.config.BiliUserData;
import com.wanfeng.myweb.crawler.dto.GuessGame;
import com.wanfeng.myweb.crawler.dto.GuessResult;
import com.wanfeng.myweb.crawler.dto.GuessTeam;
import com.wanfeng.myweb.crawler.properties.BiliProperties;
import com.wanfeng.myweb.crawler.service.BiliHttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompetitionGuessTask implements Task {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompetitionGuessTask.class);
    @Resource
    private BiliHttpService biliHttpUtils;
    @Resource
    private BiliProperties biliProperties;
    @Resource
    private UserClient userClient;

    // 打印double时只保留两位小数
    public static String formatTwoNum(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.toString();
    }

    @Override
    public void run() {
        BiliUserData biliUserData = ThreadLocalUtils.get(ThreadLocalUtils.BILI_USER_DATA, BiliUserData.class);
        try {
            ArrayList<GuessGame> guessingList = getGuessingList();
            if (guessingList == null || guessingList.isEmpty()) {
                LOGGER.info("今日无竞猜");
                return;
            }
            for (GuessGame guessGame : guessingList) {
                // 只在比赛前一天进行竞猜
                if (isOneDayBeforeOrNow(guessGame.getEndTime())) {
                    doGuess(guessGame);
                }
            }
            doYesterdayRecord();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            LOGGER.info("竞猜失败:{}", e.getMessage());
            biliUserData.info("竞猜失败:{}", e.getMessage());
        }
    }

    public void doYesterdayRecord() {
        BiliUserData biliUserData = ThreadLocalUtils.get(ThreadLocalUtils.BILI_USER_DATA, BiliUserData.class);
        JSONObject record = biliHttpUtils.getWithTotalCookie("https://api.bilibili.com/x/esports/guess/collection/record?type=2&pn=1&ps=10");
        JSONArray jsonArray = record.getJSONObject("data").getJSONArray("record");
        List<GuessResult> guessResults = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject oneRecord = jsonArray.getJSONObject(i).getJSONArray("guess").getJSONObject(0);
            GuessResult guessResult = new GuessResult(oneRecord);
            if (isYesterdayRecord(guessResult.getTime())) {
                guessResults.add(guessResult);
            }
        }
        int success = 0;
        int defeat = 0;
        double totalIncome = 0;
        for (GuessResult guessResult : guessResults) {
            if (guessResult.getRes()) {
                success++;
                totalIncome += guessResult.getIncome();
            } else {
                defeat++;
            }
        }
        int totalNum = success + defeat;
        double sum = totalNum * 10;
        double profit = totalIncome - sum;
        String prfStr = profit >= 0L ? "盈利" : "亏损";
        biliUserData.info("近日竞猜战绩：共竞猜:「" + totalNum + "」场 | 胜利：「" + success + "」场 ｜ " + " 失败：「" + defeat + "」场 ｜ " + "共使用「" + sum + "」个硬币 | 共消费：「" + formatTwoNum(totalIncome) + "」个硬币" + " | " + prfStr + "：「" + formatTwoNum(Math.abs(profit)) + "」个硬币", "");
        LOGGER.info("近日竞猜战绩：共竞猜:「" + totalNum + "」场 | 胜利：「" + success + "」场 ｜ " + " 失败：「" + defeat + "」场 ｜ " + "共使用「" + sum + "」个硬币 | 共消费：「" + formatTwoNum(totalIncome) + "」个硬币" + " | " + prfStr + "：「" + formatTwoNum(profit) + "」个硬币");
    }

    // 只需要昨天之后的时间
    private boolean isYesterdayRecord(Long timestamp) {
        timestamp = timestamp * 1000;
        // 将时间戳转换为LocalDateTime
        LocalDateTime dateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
        // 获取昨天的日期
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        // 检查日期是否为昨天，并且时间是否在0点之后
        return dateTime.isAfter(yesterday.withHour(0).withMinute(0).withSecond(0).withNano(0));
    }

    // 执行竞猜逻辑
    private void doGuess(GuessGame guessGame) {
        BiliUserData biliUserData = ThreadLocalUtils.get(ThreadLocalUtils.BILI_USER_DATA, BiliUserData.class);
        ArrayList<GuessTeam> guessOptions = guessGame.getGuessOptions();
        double odds = Double.parseDouble(guessOptions.get(0).getTeamRate());
        GuessTeam chooseOpt = guessOptions.get(0);
        for (GuessTeam guessOption : guessOptions) {
            // 大于号表示正向压
            if (odds > Double.parseDouble(guessOption.getTeamRate())) {
                odds = Double.parseDouble(guessOption.getTeamRate());
                chooseOpt = guessOption;
            }
        }
        // 投币给胜率高的队伍
        String body = "oid=" + guessGame.getContestId()
                + "&main_id=" + guessGame.getMainId()
                + "&detail_id=" + chooseOpt.getTeamId()
                + "&count=" + biliProperties.getGuessCoin()
                + "&is_fav=1"
                + "&csrf=" + biliUserData.getBiliJct();
        JSONObject post = biliHttpUtils.postWithTotalCookie("https://api.bilibili.com/x/esports/guess/add", body);
        if (post.getString("code").equals("0")) {
            LOGGER.info("在{}中投给{}{}个硬币", guessGame.getTitle(), chooseOpt.getTeamName(), biliProperties.getGuessCoin());
            biliUserData.info("在" + guessGame.getTitle() + "中投给" + chooseOpt.getTeamName() + biliProperties.getGuessCoin() + "个硬币");
        } else {
            LOGGER.info("在{" + guessGame.getTitle() + "}中竞猜: {}", post.getString("message"));
        }
    }

    private ArrayList<GuessGame> getGuessingList() {
        ArrayList<GuessGame> guessGameArrayList = new ArrayList<>();
        JSONObject jsonObject = biliHttpUtils.getWithTotalCookie("https://api.bilibili.com/x/esports/guess/collection/question?pn=1&ps=50");
        if (jsonObject.getString("code").equals("0")) {
            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("list");
            for (Object o : jsonArray) {
                JSONObject json = (JSONObject) o;
                guessGameArrayList.add(new GuessGame(json));
            }
            return guessGameArrayList;
        } else {
            return null;
        }
    }

    boolean isOneDayBeforeOrNow(String timestamp) {
        Instant instant = Instant.ofEpochSecond(Long.parseLong(timestamp));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateTime = instant.atZone(ZoneId.of("UTC")).toLocalDateTime();
        String formattedDateTime = dateTime.format(formatter);
        LocalDate date = LocalDate.parse(formattedDateTime);
        LocalDate today = LocalDate.now();
        LocalDate yesterdayOfEnd = date.minus(Period.ofDays(1));
        // 判断今天是否是输入日期的前一天或今天
        return today.equals(yesterdayOfEnd) || today.equals(date);
    }
}
