package com.feicheng.authority.monitor.service;

import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.monitor.entity.ActiveUser;

/**
 * @author Lenovo
 */
public interface SessionService {

    // 查询全部的在线用户
    ResponseResult<ActiveUser> list(String userName);
}
