package com.feicheng.authority.system.controller.login;

import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.system.service.AdminService;
import com.feicheng.authority.utils.EmailUtil;
import com.feicheng.authority.utils.RandomValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录Controller
 *
 * @author Lenovo
 */
@Controller
public class LoginController {

    @Autowired
    private TemplateEngine templateEngine;


    @Autowired(required = false)
    private AdminService adminService;

    private RandomValidateCode randomValidateCode = new RandomValidateCode();


    /**
     * 登录操作
     *
     * @param adminName
     * @param adminPassword
     * @param captcha
     * @param request
     * @return
     */
    @PostMapping("login")
    public ResponseEntity<ResponseResult<Void>> login(@RequestParam("adminName") String adminName,
                                                      @RequestParam("adminPassword") String adminPassword,
                                                      @RequestParam("captcha") String captcha,
                                                      HttpServletRequest request) {

        ResponseResult<Void> responseResult = this.adminService.login(adminName, adminPassword, captcha, request);

        return ResponseEntity.ok(responseResult);
    }

    /**
     * 注册操作
     *
     * @param adminName
     * @param adminPassword
     * @param captcha
     * @param adminEmail
     * @return
     */
    @PostMapping("register")
    public ResponseEntity<ResponseResult<Void>> register(@RequestParam("adminName") String adminName,
                                                         @RequestParam("adminPassword") String adminPassword,
                                                         @RequestParam("captcha") String captcha,
                                                         @RequestParam("adminEmail") String adminEmail,
                                                         HttpServletRequest request) {

        ResponseResult<Void> responseResult = this.adminService.register(adminName, adminPassword, captcha, adminEmail, request);

        return ResponseEntity.ok(responseResult);
    }

    /**
     * 获取验证码
     *
     * @param request
     * @param response
     */
    @GetMapping("getCode")
    public void getCode(HttpServletRequest request, HttpServletResponse response) {

        try {

            randomValidateCode.getRandcode(request, response);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
