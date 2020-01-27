package com.feicheng.authority.system.controller.role;

import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.system.entity.Role;
import com.feicheng.authority.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 角色Controller
 * @author Lenovo
 */
@Controller
@RequestMapping("role")
public class RoleController {


    @Autowired(required = false)
    private RoleService roleService;


    /**
     * 分页查询所有的角色信息
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<ResponseResult<Role>> list(@RequestParam("page") Integer page,
                                                     @RequestParam("limit") Integer limit,
                                                     @RequestParam(value = "roleName", required = false) String roleName) {

        ResponseResult<Role> roleResponseResult = this.roleService.list(page, limit, roleName);

        return ResponseEntity.ok(roleResponseResult);
    }


    /**
     * 修改角色信息及权限
     * @param role
     * @return
     */
    @PostMapping("edit")
    public ResponseEntity<ResponseResult<Void>> edit(Role role){

        ResponseResult<Void> responseResult = this.roleService.edit(role);

        return ResponseEntity.ok(responseResult);
    }

    /**
     * 删除角色信息及权限
     * @param roleId
     * @return
     */
    @PostMapping("delete")
    public ResponseEntity<ResponseResult<Void>> delete(@RequestParam("roleId") Long roleId){

        ResponseResult<Void> responseResult = this.roleService.delete(roleId);

        return ResponseEntity.ok(responseResult);
    }

    /**
     * 添加角色及权限
     * @param role
     * @return
     */
    @PostMapping("add")
    public ResponseEntity<ResponseResult<Void>> add(Role role){

        ResponseResult<Void> responseResult = this.roleService.add(role);

        return ResponseEntity.ok(responseResult);
    }
}
