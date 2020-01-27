package com.feicheng.authority.system.config;

import com.feicheng.authority.common.response.MessageResult;
import com.feicheng.authority.system.entity.Admin;
import com.feicheng.authority.system.entity.Menu;
import com.feicheng.authority.system.entity.Role;
import com.feicheng.authority.system.service.AdminService;
import com.feicheng.authority.system.service.MenuService;
import com.feicheng.authority.system.service.RoleService;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义实现 ShiroRealm，包含认证和授权两大模块
 *
 * @author Lenovo
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;


    /**
     * 授权模块，获取用户角色和权限
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();

        // 获取管理员名
        String adminName = admin.getAdminName();

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 根据管理员名获取角色集
        List<Role> roles = this.roleService.selectAdminRole(adminName);

        Set<String> roleSet = roles.stream().map(Role::getRoleName).collect(Collectors.toSet());

        simpleAuthorizationInfo.setRoles(roleSet);

        // 获取用户权限集
        List<Menu> permissionList = this.menuService.selectAdminPermission(adminName);

        Set<String> permissionSet = permissionList.stream().map(Menu::getAuthority).collect(Collectors.toSet());

        simpleAuthorizationInfo.setStringPermissions(permissionSet);

        return simpleAuthorizationInfo;

    }


    /**
     * 用户认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        // 获取管理员输入的管理员名和密码
        String adminName = (String) authenticationToken.getPrincipal();

        String adminPassword = new String((char[]) authenticationToken.getCredentials());


        // 根据管理员名查询管理员信息
        MessageResult<Admin> messageResult = this.adminService.selectAdminByName(adminName);

        // 判断账号是否注册
        if (messageResult.getData() == null) {

            throw new UnknownAccountException("账号未注册");
        }

        // 判断密码是否正确
        if (!StringUtils.equals(adminPassword, messageResult.getData().getAdminPassword())) {

            throw new IncorrectCredentialsException("用户名或密码错误");
        }

        // 判断账户是否激活
        if (messageResult.getData().getAdminStatus() == 0) {

            throw new AuthenticationException("账户未激活");
        }

        return new SimpleAuthenticationInfo(messageResult.getData(), messageResult.getData().getAdminPassword(), getName());
    }
}
