package com.feicheng.authority.monitor.controller;

import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.monitor.entity.ActiveUser;
import com.feicheng.authority.monitor.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 在线用户Controller
 *
 * @author Lenovo
 */
@Controller
@RequestMapping("active")
public class SessionController {


    @Autowired(required = false)
    private SessionService sessionService;

    /**
     * 获取在线用户
     * @param userName
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<ResponseResult<ActiveUser>> list(@RequestParam("userName") String userName) {

        ResponseResult<ActiveUser> responseResult = this.sessionService.list(userName);

        return ResponseEntity.ok(responseResult);
    }
}
