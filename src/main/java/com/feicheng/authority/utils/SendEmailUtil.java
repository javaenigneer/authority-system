package com.feicheng.authority.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 邮箱发送
 *
 * @author Lenovo
 */
public class SendEmailUtil {

    public static Logger logger = LoggerFactory.getLogger(SendEmailUtil.class);

    public static void sendEmail(String receiveEmail, String msg) {

        //做链接前的准备工作  也就是参数初始化
        Properties properties = new Properties();

        //发送邮箱服务器
        properties.setProperty("mail.smtp.host", "smtp.qq.com");

        //发送端口
        properties.setProperty("mail.smtp.port", "465");

        //是否开启权限控制
        properties.setProperty("mail.smtp.auth", "true");

        //true 打印信息到控制台
        properties.setProperty("mail.debug", "true");

        //发送的协议是简单的邮件传输协议
        properties.setProperty("mail.transport", "smtp");

        properties.setProperty("mail.smtp.ssl.enable", "true");

        //建立两点之间的链接
        logger.info("两点之间已连接");

        Session session = Session.getInstance(properties, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("1692454247@qq.com", "okwoouyumsnrcajh");

            }
        });

        System.out.println("执行了3");

        //创建邮件对象
        Message message = new MimeMessage(session);

        //设置发件人
        try {

            message.setFrom(new InternetAddress("1692454247@qq.com"));

            //设置收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiveEmail));

            //设置主题
            message.setSubject("邮件消息");

            //设置邮件正文  第二个参数是邮件发送的类型
            message.setContent(msg, "text/html;charset=UTF-8");

            //发送一封邮件
            Transport transport = session.getTransport();

            transport.connect("1692454247@qq.com", "okwoouyumsnrcajh");

            Transport.send(message);

            logger.info("邮件已发送");

        } catch (MessagingException e) {

            e.printStackTrace();

            logger.error("邮件发送出错");

        } finally {


        }

    }

}
