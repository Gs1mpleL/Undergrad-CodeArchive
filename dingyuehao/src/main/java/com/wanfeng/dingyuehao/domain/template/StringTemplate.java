package com.wanfeng.dingyuehao.domain.template;

public class StringTemplate {
    public final static String WeatherTemplate =
            "%s：\n" +
            "天气:[%s]\n" +
            "气温:[%s]\n" +
            "风力:[%s]\n" +
            "未来天气:[%s]";


    public final static String PaymentTemplate =
            "%s:\n" +
            "消费:[%s]\n" +
            "收入:[%s]\n" +
            "结余:[%s]";

    public final static String PaymentDelTemplate = "删除成功!\n" +
            "今日:\n" +
            "消费:[%s]\n" +
            "收入:[%s]\n" +
            "结余:[%s]";

    public final static String PaymentNULLTemplate = "删除成功!\n本日无消费记录";

    public final static String NoteRecord = "%s日志:\n" +
            "%s";
}
