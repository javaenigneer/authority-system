package com.feicheng.authority.system.controller;

import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.system.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;

/**
 * 邮件发送Controller
 *
 * @author Lenovo
 */
@Controller
@RequestMapping("mail")
public class MailController {


    @Autowired
    private MailService mailService;


    /**
     * 邮件发送
     *
     * @return
     */
    @PostMapping("toEmail")
    public ResponseEntity<ResponseResult<Void>> toEmail(@RequestParam("toEmail") String toEmail,
                                                        @RequestParam("content") String content) throws MessagingException {

        ResponseResult<Void> responseResult = this.mailService.toEmail(toEmail, content);

        return ResponseEntity.ok(responseResult);
    }
}
