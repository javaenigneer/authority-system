package com.feicheng.authority.system.service.impl;

import com.feicheng.authority.system.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lenovo
 */
@Service
public class LoginServiceImpl implements LoginService {

    private Subject getSubject() {

        return SecurityUtils.getSubject();
    }

    @Override
    public void login(AuthenticationToken token) {

        getSubject().login(token);
    }
}
