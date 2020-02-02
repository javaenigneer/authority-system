package com.feicheng.authority.system.service.impl;

import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.system.service.MailService;
import com.feicheng.authority.utils.SendEmailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 邮件发送Service
 *
 * @author Lenovo
 */
@Service
public class MailServiceImpl implements MailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

    /**
     * 邮件发送
     *
     * @return
     */
    @Override
    public ResponseResult<Void> toEmail(String toEmail, String content) throws MessagingException {


        try {

            SendEmailUtil.sendEmail(toEmail, content);

            LOGGER.info("邮件发送成功");

            return new ResponseResult<>(200, "发送成功");

        } catch (Exception e) {

            e.printStackTrace();

            LOGGER.error("邮件发送成功");

            return new ResponseResult<>(500, "发送失败");
        }
    }
}
