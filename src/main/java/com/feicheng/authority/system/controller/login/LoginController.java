package com.feicheng.authority.system.controller.login;

import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.system.service.AdminService;
import com.feicheng.authority.utils.RandomValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录Controller
 * @author Lenovo
 */
@Controller
public class LoginController {

    @Autowired(required = false)
    private AdminService adminService;

    private RandomValidateCode randomValidateCode = new RandomValidateCode();


    /**
     * 登录操作
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
                                                      HttpServletRequest request){

        ResponseResult<Void> responseResult  = this.adminService.login(adminName, adminPassword, captcha, request);

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
