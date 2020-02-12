package com.feicheng.authority.job.controller.joblog;

import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.job.entity.JobLog;
import com.feicheng.authority.job.service.JobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 任务日志Controller
 *
 * @author Lenovo
 */
@Controller
@RequestMapping("jobLog")
public class JobLogController {


    @Autowired
    private JobLogService jobLogService;


    /**
     * 分页查询全部的日志信息
     *
     * @param page
     * @param limit
     * @param start
     * @param end
     * @param beanName
     * @param status
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<ResponseResult<JobLog>> list(@RequestParam("page") Integer page,
                                                       @RequestParam("limit") Integer limit,
                                                       @RequestParam(value = "start", required = false) String start,
                                                       @RequestParam(value = "end", required = false) String end,
                                                       @RequestParam(value = "beanName", required = false) String beanName,
                                                       @RequestParam(value = "status", required = false) Integer status) {

        ResponseResult<JobLog> responseResult = this.jobLogService.list(page, limit, start, end, beanName, status);

        return ResponseEntity.ok(responseResult);
    }


    /**
     * 删除任务调度日志
     *
     * @param jobLogId
     * @return
     */
    @PostMapping("delete")
    public ResponseEntity<ResponseResult<Void>> delete(@RequestParam("jobLogId") Long jobLogId) {

        ResponseResult<Void> responseResult = this.jobLogService.delete(jobLogId);

        return ResponseEntity.ok(responseResult);
    }

    /**
     * 批量删除任务调度日志
     *
     * @param jobLogIds
     * @return
     */
    @PostMapping("deleteIds")
    public ResponseEntity<ResponseResult<Void>> deleteIds(@RequestParam("jobLogIds") String jobLogIds) {

        ResponseResult<Void> responseResult = this.jobLogService.deleteIds(jobLogIds);

        return ResponseEntity.ok(responseResult);
    }

}
