package com.feicheng.authority.system.service;

import com.feicheng.authority.common.response.MenuTree;
import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.system.entity.Menu;

import java.util.List;

/**
 * @author Lenovo
 */
public interface MenuService {

    // 查询菜单
    ResponseResult<Menu> list();

    // 修改菜单
    ResponseResult<Void> edit(Menu menu);

    // 添加菜单
    ResponseResult<Void> add(Menu menu);

    // 删除菜单
    ResponseResult<Void> delete(Long authorityId, Integer isMenu);

    // 获取节点树的所有数据
    MenuTree<Menu> selectNodes();

    // 根据管理员获取管理员的菜单权限
    List<Menu> selectAdminPermission(String adminName);
}
