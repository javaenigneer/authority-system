package com.feicheng.authority.system.service;

import java.util.List;

/**
 * @author Lenovo
 */
public interface RoleMenuService {

    // 根据角色Id获取该角色的权限菜单
    List<Long> select(Long roleId);
}
