package com.feicheng.authority.system.controller.login;

import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.system.service.AdminService;
import com.feicheng.authority.utils.EmailUtil;
import com.feicheng.authority.utils.RandomValidateCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
     * @return
     */
    @PostMapping("login")
    public ResponseEntity<ResponseResult<Void>> login(@RequestParam("adminName") String adminName,
                                                      @RequestParam("adminPassword") String adminPassword) {

        ResponseResult<Void> responseResult = this.adminService.login(adminName, adminPassword);

        return ResponseEntity.ok(responseResult);
    }

    /**
     * 注册操作
     *
     * @param adminName
     * @param adminPassword
     * @param adminEmail
     * @return
     */
    @PostMapping("register")
    public ResponseEntity<ResponseResult<Void>> register(@RequestParam("adminName") String adminName,
                                                         @RequestParam("adminPassword") String adminPassword,
                                                         @RequestParam("adminEmail") String adminEmail) {

        ResponseResult<Void> responseResult = this.adminService.register(adminName, adminPassword,  adminEmail);

        return ResponseEntity.ok(responseResult);
    }

    /**
     * 退出登录
     *
     * @return
     */
    @PostMapping("logOut")
    public ResponseEntity<ResponseResult<Void>> logOut() {

        try {

            Subject subject = SecurityUtils.getSubject();

            subject.logout();

            return ResponseEntity.ok(new ResponseResult<>(200, "登出成功"));

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.ok(new ResponseResult<>(500, "登出失败"));
        }


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
