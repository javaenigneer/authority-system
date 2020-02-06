package com.feicheng.authority.system.controller.admin;

import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.system.entity.Admin;
import com.feicheng.authority.system.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Date;

/**
 * 管理员Controller
 *
 * @author Lenovo
 */
@Controller
@RequestMapping("admin")
public class AdminController {


    @Autowired(required = false)
    private AdminService adminService;

    /**
     * 查询全部的管理员
     *
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<ResponseResult<Admin>> list(@RequestParam("page") Integer page,
                                                      @RequestParam("limit") Integer limit,
                                                      @RequestParam(value = "start", required = false) String start,
                                                      @RequestParam(value = "end", required = false) String end,
                                                      @RequestParam(value = "adminName", required = false) String adminName,
                                                      @RequestParam(value = "adminEmail", required = false) String adminEmail,
                                                      @RequestParam(value = "adminPhone", required = false) String adminPhone) {


        ResponseResult<Admin> responseResult = this.adminService.list(page, limit, start, end, adminName, adminEmail, adminPhone);

        return ResponseEntity.ok(responseResult);
    }


    /**
     * 修改管理员
     *
     * @param admin
     * @return
     */
    @PostMapping("edit")
    public ResponseEntity<ResponseResult<Void>> edit(Admin admin) {

        ResponseResult<Void> responseResult = this.adminService.edit(admin);

        return ResponseEntity.ok(responseResult);
    }


    /**
     * 删除管理员
     *
     * @param adminId
     * @return
     */
    @PostMapping("delete")
    public ResponseEntity<ResponseResult<Void>> delete(@RequestParam("adminId") Long adminId) {

        ResponseResult<Void> responseResult = this.adminService.delete(adminId);

        return ResponseEntity.ok(responseResult);
    }

    /**
     * 批量删除管理员
     *
     * @param adminIds
     * @return
     */
    @PostMapping("deleteIds")
    public ResponseEntity<ResponseResult<Void>> deleteIds(@RequestParam("adminIds") String adminIds) {

        ResponseResult<Void> responseResult = this.adminService.deleteIds(adminIds);

        return ResponseEntity.ok(responseResult);
    }


    /**
     * 激活管理员
     *
     * @param adminId
     * @return
     */
    @GetMapping("active/{adminId}")
    public String active(@PathVariable("adminId") Long adminId, Model model) {

        ResponseResult<Void> responseResult = this.adminService.active(adminId);

        // 激活成功
        if (responseResult.getCode() == 200) {

            model.addAttribute("msg", responseResult.getMsg());

            return "authority/system/success/result";
        }

        // 激活失败
        model.addAttribute("msg", responseResult.getMsg());

        return "authority/system/error/result";

    }
}
