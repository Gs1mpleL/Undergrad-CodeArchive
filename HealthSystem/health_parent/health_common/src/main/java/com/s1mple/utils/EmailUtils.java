package com.s1mple.utils;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @author wanfeng
 * @create 2022/2/24 14:37
 */
public class EmailUtils {
    public static String myEmailAccount = "804121985@qq.com";
    public static String myEmailPassword = "pdocjktmtmasbdif";
    public static String myEmailSMTPHost = "smtp.qq.com";

    public static void sendEmail(String receiveMailAccount,String text) throws Exception {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", myEmailSMTPHost);
        props.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(props);
        session.setDebug(true);
        MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccount,text);
        Transport transport = session.getTransport();
        transport.connect(myEmailAccount, myEmailPassword);
        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());
        // 7. 关闭连接
        transport.close();
    }

    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session     和服务器交互的会话
     * @param sendMail    发件人邮箱
     * @param receiveMail 收件人邮箱
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,String text) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, "s1mpleHealth", "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "个人用户", "UTF-8"));

        // 4. Subject: 邮件主题
        message.setSubject("验证码", "UTF-8");
        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent(text, "text/html;charset=UTF-8");
        // 6. 设置发件时间
        message.setSentDate(new Date());
        // 7. 保存设置
        message.saveChanges();
        return message;
    }


    public static void main(String[] args) throws Exception {
        EmailUtils.sendEmail("804121985@qq.com","测试");
    }
}