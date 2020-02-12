package com.feicheng.authority.monitor.service;

import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.monitor.entity.LoginLog;

/**
 * 登录日志Service
 * @author Lenovo
 */
public interface LoginLogService {

    // 添加登录日志
    void add(LoginLog loginLog);

    // 分页查询全部的登录日志
    ResponseResult<LoginLog> list(Integer page, Integer limit, String start, String end, String userName);

    // 删除登录日志
    ResponseResult<Void> delete(Long id);

    // 批量删除登录日志
    ResponseResult<Void> deleteIds(String ids);
}
