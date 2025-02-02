package com.wanfeng.dingyuehao.component.commandCallBack;

import com.wanfeng.dingyuehao.domain.Enum.PaymentEnum;
import com.wanfeng.dingyuehao.domain.VO.MsgReq;
import com.wanfeng.dingyuehao.domain.VO.PaymentReq;
import com.wanfeng.dingyuehao.domain.VO.PaymentResp;
import com.wanfeng.dingyuehao.domain.template.StringTemplate;
import com.wanfeng.dingyuehao.service.PaymentService;
import com.wanfeng.dingyuehao.util.MsgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.regex.Matcher;

@Component("收入")
public class SalaryHandler implements CommandHandler{

    @Autowired
    private PaymentService paymentService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String handler(Matcher matcher, MsgReq msgReq) {
        PaymentReq paymentReq = new PaymentReq();
        paymentReq.setUserid(msgReq.getFromUserName());
        paymentReq.setType(PaymentEnum.Salary.getType());
        return insertAndReturnRecord(matcher, msgReq, paymentService,paymentReq);
    }

    static String insertAndReturnRecord(Matcher matcher, MsgReq msgReq, PaymentService paymentService, PaymentReq paymentReq) {

        switch (matcher.group(2)) {
            case "本日":
                return getOneDayRecord(msgReq, paymentService);
            case "本月":
                return getOneMonthRecord(msgReq, paymentService);
            case "全部":
                return getAllRecord(msgReq, paymentService);
            case "删除":
                return dealDel(msgReq, paymentReq, paymentService);
        }

        return dealInsert(matcher, msgReq, paymentReq, paymentService);
    }

    private static String dealInsert(Matcher matcher, MsgReq msgReq, PaymentReq paymentReq, PaymentService paymentService) {
        try {
            paymentReq.setAmount(new BigDecimal(matcher.group(2)));
        }catch (Exception e){
            return getOneDayRecord(msgReq, paymentService);
        }
        boolean insert = paymentService.addPayment(paymentReq);
        if (insert){
            return getOneDayRecord(msgReq, paymentService);
        }else {
            return MsgUtils.buildReply(msgReq, "保存失败，请联系管理员");
        }
    }

    private static String getOneDayRecord(MsgReq msgReq, PaymentService paymentService) {
        PaymentResp curDay = paymentService.getCurDay(msgReq.getFromUserName());
        if (curDay == null){
            return MsgUtils.buildReply(msgReq,"本日无记录");
        }
        String format = String.format(StringTemplate.PaymentTemplate, "本日",curDay.getPay(), curDay.getSalary(), curDay.getRemain());
        return MsgUtils.buildReply(msgReq, format);
    }

    private static String getOneMonthRecord(MsgReq msgReq, PaymentService paymentService) {
        PaymentResp curMonth = paymentService.getCurMonth(msgReq.getFromUserName());
        if (curMonth == null){
            return MsgUtils.buildReply(msgReq,"本月无记录");
        }
        String format = String.format(StringTemplate.PaymentTemplate, "本月",curMonth.getPay(), curMonth.getSalary(), curMonth.getRemain());
        return MsgUtils.buildReply(msgReq, format);
    }

    private static String getAllRecord(MsgReq msgReq, PaymentService paymentService) {
        PaymentResp all = paymentService.getAll(msgReq.getFromUserName());
        if (all == null){
            return MsgUtils.buildReply(msgReq,"无记录");
        }
        String format = String.format(StringTemplate.PaymentTemplate, "全部", all.getPay(), all.getSalary(), all.getRemain());
        return MsgUtils.buildReply(msgReq, format);
    }

    private static String dealDel(MsgReq msgReq, PaymentReq paymentReq, PaymentService paymentService) {
        boolean isDel = paymentService.delOne(paymentReq);
        if (isDel){
            PaymentResp curDay = paymentService.getCurDay(msgReq.getFromUserName());
            if (curDay == null){
                return MsgUtils.buildReply(msgReq, StringTemplate.PaymentNULLTemplate);
            }
            String format = String.format(StringTemplate.PaymentDelTemplate, curDay.getPay(), curDay.getSalary(), curDay.getRemain());
            return MsgUtils.buildReply(msgReq, format);
        }else {
            return MsgUtils.buildReply(msgReq, "删除失败，失效过期");
        }
    }

    @Override
    public String desc() {
        return "添加一条收入记录\n收入 [金额/本日/本月/全部]";
    }
}
