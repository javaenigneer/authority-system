package com.feicheng.authority.system.service;

import com.feicheng.authority.common.response.ResponseResult;

import javax.mail.MessagingException;

/**
 * 邮件发送Service
 * @author Lenovo
 */
public interface MailService {

    // 邮件发送
    ResponseResult<Void> toEmail(String toEmail, String content) throws MessagingException;
}
