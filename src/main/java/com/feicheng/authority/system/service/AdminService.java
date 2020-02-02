package com.feicheng.authority.system.service;

import com.feicheng.authority.common.response.MessageResult;
import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.system.entity.Admin;
import org.springframework.data.domain.Pageable;


import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author Lenovo
 */
public interface AdminService {

    // 查询所有的管理员
    ResponseResult<Admin> list(Integer page, Integer limit, String start, String end, String adminName, String adminEmail, String adminPhone);

    // 修改管理员
    ResponseResult<Void> edit(Admin admin);

    // 删除管理员
    ResponseResult<Void> delete(Long adminId);

    // 批量删除管理员
    ResponseResult<Void> deleteIds(String adminIds);

    // 登录
    ResponseResult<Void> login(String adminName, String adminPassword, String captcha, HttpServletRequest request);

    // 根据用户名查询管理员
    MessageResult<Admin> selectAdminByName(String adminName);

    // 注册操作
    ResponseResult<Void> register(String adminName, String adminPassword, String captcha, String adminEmail, HttpServletRequest request);

    // 激活管理员
    ResponseResult<Void> active(Long adminId);
}
