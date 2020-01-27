package com.feicheng.authority.system.service;

import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.system.entity.Role;

import java.util.List;

/**
 * @author Lenovo
 */
public interface RoleService {

    // 分页查询角色
    ResponseResult<Role> list(Integer page, Integer limit, String roleName);

    // 修改角色信息及权限
    ResponseResult<Void> edit(Role role);

    // 删除角色信息及权限
    ResponseResult<Void> delete(Long roleId);

    // 添加角色及权限
    ResponseResult<Void> add(Role role);

    // 根据管理员名成获取角色集
    List<Role> selectAdminRole(String adminName);
}
