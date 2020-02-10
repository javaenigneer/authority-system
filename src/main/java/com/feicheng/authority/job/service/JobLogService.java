package com.feicheng.authority.job.service;

import com.feicheng.authority.job.entity.JobLog;

/**
 * @author Lenovo
 */
public interface JobLogService {

    // 保存任务调度日志
    void save(JobLog jobLog);
}
