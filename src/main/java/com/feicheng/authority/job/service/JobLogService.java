package com.feicheng.authority.job.service;

import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.job.entity.JobLog;

/**
 * @author Lenovo
 */
public interface JobLogService {

    // 保存任务调度日志
    void save(JobLog jobLog);

    // 分页查询全部的日志信息
    ResponseResult<JobLog> list(Integer page, Integer limit, String start, String end, String beanName, Integer status);

    // 删除任务调度日志
    ResponseResult<Void> delete(Long jobLogId);

    // 批量删除任务调度日志
    ResponseResult<Void> deleteIds(String jobLogIds);
}

