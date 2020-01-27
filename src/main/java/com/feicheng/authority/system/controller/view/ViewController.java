package com.feicheng.authority.system.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 页面控制Controller
 *
 * @author Lenovo
 */
@Controller
@RequestMapping("admin")
public class ViewController {


    /**
     * 后台主界面
     *
     * @return
     */
    @RequestMapping("index.html")
    public String showIndex() {

        return "authority/index";
    }


    /**
     * 管理员界面---静态表格
     *
     * @return
     */
    @RequestMapping("list.html")
    public String showAdminList() {

        return "authority/system/admin/admin-list";
    }

    /**
     * 管理员编辑界面
     *
     * @return
     */
    @RequestMapping("edit.html")
    public String showAdminEdit() {

        return "authority/system/admin/admin-edit";
    }


    /**
     * 欢迎界面
     *
     * @return
     */
    @RequestMapping("welcome.html")
    public String showWelcome() {

        return "authority/system/welcome/welcome-1";
    }


    /**
     * 打开菜单界面
     *
     * @return
     */
    @RequestMapping("menu.html")
    public String showMenu() {

        return "authority/system/menu/menu-list";
    }


    /**
     * 编辑菜单界面
     * @return
     */
    @RequestMapping("menu/edit.html" )
    public String showEditMenu() {

        return "authority/system/menu/menu-add";
    }

    /**
     * 添加菜单界面
     * @return
     */
    @RequestMapping("menu/add.html" )
    public String showAddMenu() {

        return "authority/system/menu/menu-add";
    }

    /**
     * 角色显示界面
     * @return
     */
    @RequestMapping("role.html")
    public String showRole(){

        return "authority/system/role/role-list";
    }

    /**
     * 角色修改界面
     * @return
     */
    @RequestMapping("role/edit.html")
    public String showRoleEdit(){

        return "authority/system/role/role-edit";
    }

    /**
     * 添加角色界面
     * @return
     */
    @RequestMapping("role/add.html")
    public String showRoleAdd(){

        return "authority/system/role/role-add";
    }

    /**
     * 登录界面
     * @return
     */
    @RequestMapping("login.html")
    public String showLogin(){

        return "authority/system/login/login";
    }

}
