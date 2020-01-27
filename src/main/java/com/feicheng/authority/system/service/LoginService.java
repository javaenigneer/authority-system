package com.feicheng.authority.system.service;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author Lenovo
 */
public interface LoginService {

    void login(AuthenticationToken token);
}
