package com.feicheng.authority.system.service.impl;

import com.feicheng.authority.system.entity.RoleMenu;
import com.feicheng.authority.system.repository.RoleMenuRepository;
import com.feicheng.authority.system.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色菜单Service
 *
 * @author Lenovo
 */
@Service
public class RoleMenuServiceImpl implements RoleMenuService {


    @Autowired(required = false)
    private RoleMenuRepository roleMenuRepository;

    /**
     * 根据角色Id获取该角色的权限菜单
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Long> select(Long roleId) {

        List<Long> roleMenus = this.roleMenuRepository.select(roleId);

        if (CollectionUtils.isEmpty(roleMenus)) {

            // 获取全部权限
            roleMenus = new ArrayList<>();

            roleMenus.add(1L);

            return roleMenus;
        }

        // 直接返回数据
        return roleMenus;
    }
}
