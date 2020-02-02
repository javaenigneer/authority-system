package com.feicheng.authority.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 邮件发送Util
 * @author Lenovo
 */
public class EmailUtil {

    @Autowired
    private JavaMailSenderImpl javaMailSender;


    @Value("${spring.mail.username}")
    private String userName;

    @Async  //意思是异步调用这个方法
    public void sendMail(String title, String url, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        // 发送人的邮箱
        message.setFrom(userName);
        //标题
        message.setSubject(title);
        //发给谁  对方邮箱
        message.setTo(email);
        //内容
        message.setText(url);
        //发送
        javaMailSender.send(message);
    }

    public void sendHtmlMail(String to, String subject, String centent) throws MessagingException {
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(userName);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(centent, true);
        javaMailSender.send(mimeMessage);
    }
}
