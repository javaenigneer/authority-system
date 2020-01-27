package com.feicheng.authority.system.controller.rolemenu;

import com.feicheng.authority.system.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 角色菜单Controller
 *
 * @author Lenovo
 */
@Controller
@RequestMapping("roleMenu")
public class RoleMenuController {

    @Autowired(required = false)
    private RoleMenuService roleMenuService;


    /**
     * 根据角色Id获取该角色的权限菜单
     *
     * @param roleId
     * @return
     */
    @GetMapping("select")
    public ResponseEntity<List<Long>> select(@RequestParam("roleId") Long roleId) {

        List<Long> roleMenus = this.roleMenuService.select(roleId);

        return ResponseEntity.ok(roleMenus);
    }
}
