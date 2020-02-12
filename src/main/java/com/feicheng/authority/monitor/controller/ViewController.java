package com.feicheng.authority.monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转Controller
 * @author Lenovo
 */
@Controller
@RequestMapping("admin")
public class ViewController {


    /**
     * 登录日志显示界面
     * @return
     */
    @RequestMapping("loginLog.html")
    public String showLoginLog(){

        return "authority/monitor/loginLog-list";
    }
}
