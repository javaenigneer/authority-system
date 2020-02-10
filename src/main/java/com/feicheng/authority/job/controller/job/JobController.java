package com.feicheng.authority.job.controller.job;

import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.job.entity.Job;
import com.feicheng.authority.job.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * 任务调度Controller
 *
 * @author Lenovo
 */
@Controller
@RequestMapping("job")
public class JobController {


    @Autowired(required = false)
    private JobService jobService;


    /**
     * 查询全部的任务调度
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<ResponseResult<Job>> list(@RequestParam("page") Integer page,
                                                    @RequestParam("limit") Integer limit,
                                                    @RequestParam(value = "start", required = false) String start,
                                                    @RequestParam(value = "end", required = false) String end,
                                                    @RequestParam(value = "beanName", required = false) String beanName,
                                                    @RequestParam(value = "status", required = false) Integer status) {

        ResponseResult<Job> responseResult = this.jobService.list(page, limit, start, end, beanName, status);

        return ResponseEntity.ok(responseResult);
    }

    /**
     * 编辑任务调度
     *
     * @param job
     * @return
     */
    @PostMapping("edit")
    public ResponseEntity<ResponseResult<Void>> edit(Job job) {

        ResponseResult<Void> responseResult = this.jobService.edit(job);

        return ResponseEntity.ok(responseResult);
    }

    /**
     * 添加任务调度
     *
     * @param job
     * @return
     */
    @PostMapping("add")
    public ResponseEntity<ResponseResult<Void>> add(Job job) {

        ResponseResult<Void> responseResult = this.jobService.add(job);

        return ResponseEntity.ok(responseResult);
    }


    /**
     * 启动任务
     *
     * @param jobId
     * @return
     */
    @PostMapping("resume")
    public ResponseEntity<ResponseResult<Void>> start(@RequestParam("jobId") Long jobId) {

        ResponseResult<Void> responseResult = this.jobService.resume(jobId);

        return ResponseEntity.ok(responseResult);
    }

    /**
     * 暂停任务
     *
     * @param jobId
     * @return
     */
    @PostMapping("end")
    public ResponseEntity<ResponseResult<Void>> end(@RequestParam("jobId") Long jobId) {

        ResponseResult<Void> responseResult = this.jobService.end(jobId);

        return ResponseEntity.ok(responseResult);
    }

    /**
     * 执行任务
     * @param jobIds
     * @return
     */
    @PostMapping("start")
    public ResponseEntity<ResponseResult<Void>> start(@RequestParam("jobIds") String jobIds) {

        ResponseResult<Void> responseResult = this.jobService.start(jobIds);

        return ResponseEntity.ok(responseResult);
    }

    /**
     * 删除任务调度
     *
     * @param jobId
     * @return
     */
    @PostMapping("delete")
    public ResponseEntity<ResponseResult<Void>> delete(@RequestParam("jobId") Long jobId) {

        ResponseResult<Void> responseResult = this.jobService.delete(jobId);

        return ResponseEntity.ok(responseResult);
    }


    /**
     * 删除部分任务调度
     *
     * @param jobIds
     * @return
     */
    @PostMapping("deleteIds")
    public ResponseEntity<ResponseResult<Void>> deleteIds(@RequestParam("jobIds") String jobIds) {

        ResponseResult<Void> responseResult = this.jobService.deleteIds(jobIds);

        return ResponseEntity.ok(responseResult);
    }
}
