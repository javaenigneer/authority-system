package com.feicheng.authority.job.service.impl;

import com.feicheng.authority.job.entity.JobLog;
import com.feicheng.authority.job.repository.JobLogRepository;
import com.feicheng.authority.job.service.JobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * JobLogService
 *
 * @author Lenovo
 */
@Service
public class JobLogServiceImpl implements JobLogService {


    @Autowired(required = false)
    private JobLogRepository jobLogRepository;

    /**
     * 保存任务调度日志
     *
     * @param jobLog
     */
    @Override
    public void save(JobLog jobLog) {

        try {

            this.jobLogRepository.saveAndFlush(jobLog);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
