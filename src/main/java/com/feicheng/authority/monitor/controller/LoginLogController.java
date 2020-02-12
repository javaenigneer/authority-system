package com.feicheng.authority.monitor.controller;

import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.monitor.entity.LoginLog;
import com.feicheng.authority.monitor.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 登录日志Controller
 *
 * @author Lenovo
 */
@Controller
@RequestMapping("loginLog")
public class LoginLogController {


    @Autowired(required = false)
    private LoginLogService loginLogService;


    /**
     * 分页查询登录日志
     *
     * @param page
     * @param limit
     * @param start
     * @param end
     * @param userName
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<ResponseResult<LoginLog>> list(@RequestParam("page") Integer page,
                                                         @RequestParam("limit") Integer limit,
                                                         @RequestParam(value = "start", required = false) String start,
                                                         @RequestParam(value = "end", required = false) String end,
                                                         @RequestParam(value = "userName", required = false) String userName) {

        ResponseResult<LoginLog> responseResult = this.loginLogService.list(page, limit, start, end, userName);

        return ResponseEntity.ok(responseResult);
    }

    /**
     * 删除登录日志
     *
     * @param id
     * @return
     */
    @PostMapping("delete")
    public ResponseEntity<ResponseResult<Void>> delete(@RequestParam("id") Long id) {

        ResponseResult<Void> responseResult = this.loginLogService.delete(id);

        return ResponseEntity.ok(responseResult);
    }


    /**
     * 批量删除登录日志
     *
     * @param ids
     * @return
     */
    @PostMapping("deleteIds")
    public ResponseEntity<ResponseResult<Void>> deleteIds(@RequestParam("ids") String ids) {

        ResponseResult<Void> responseResult = this.loginLogService.deleteIds(ids);

        return ResponseEntity.ok(responseResult);
    }
}
