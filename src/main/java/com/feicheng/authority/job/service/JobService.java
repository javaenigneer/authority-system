package com.feicheng.authority.job.service;

import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.job.entity.Job;

/**
 * @author Lenovo
 */
public interface JobService {

    // 分页查询全部的任务调度
    ResponseResult<Job> list(Integer page, Integer limit, String start, String end, String beanName, Integer status);

    // 启动任务
    ResponseResult<Void> resume(Long jobId);

    // 暂停任务
    ResponseResult<Void> end(Long jobId);

    // 编辑任务
    ResponseResult<Void> edit(Job job);

    // 添加任务调度
    ResponseResult<Void> add(Job job);

    // 删除任务调度
    ResponseResult<Void> delete(Long jobId);

    // 删除部分任务调度
    ResponseResult<Void> deleteIds(String jobIds);

    // 执行任务
    ResponseResult<Void> start(String jobIds);
}
