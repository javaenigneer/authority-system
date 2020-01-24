package com.feicheng.authority.system.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面控制Controller
 * @author Lenovo
 */
@Controller
public class ViewController {


    /**
     * 后台主界面
     * @return
     */
    @RequestMapping("admin/index.html")
    public String showIndex(){

        return "authority/index";
    }


    /**
     * 管理员界面---静态表格
     * @return
     */
    @RequestMapping("admin/list.html")
    public String showAdminList(){

        return "authority/system/admin/admin-list";
    }

    /**
     * 管理员编辑界面
     * @return
     */
    @RequestMapping("admin/edit.html" )
    public String showAdminEdit(){

        return "authority/system/admin/admin-edit";
    }


    /**
     * 欢迎界面
     * @return
     */
    @RequestMapping("admin/welcome.html" )
    public String showWelcome(){

        return "authority/system/welcome/welcome-1";
    }

    
}
