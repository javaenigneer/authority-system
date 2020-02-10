package com.feicheng.authority.job.util;


import com.feicheng.authority.job.entity.Job;
import com.feicheng.authority.job.entity.JobLog;
import com.feicheng.authority.job.service.JobLogService;
import com.feicheng.authority.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 定时任务
 *
 * @author MrBird
 */
@Slf4j
public class ScheduleJob extends QuartzJobBean {


    private ExecutorService service = Executors.newSingleThreadExecutor();

    @Override
    protected void executeInternal(JobExecutionContext context) {

        Job scheduleJob = (Job) context.getMergedJobDataMap().get(Job.JOB_PARAM_KEY);

        JobLogService jobLogService = SpringContextUtil.getBean(JobLogService.class);

        // 创建日志对象
        JobLog jobLog = new JobLog();

        // 设置基本参数
        jobLog.setJobId(scheduleJob.getJobId());

        jobLog.setBeanName(scheduleJob.getBeanName());

        jobLog.setMethodName(scheduleJob.getMethodName());

        jobLog.setParams(scheduleJob.getParams());

        jobLog.setCreateTime(new Date());

        Long startTime = System.currentTimeMillis();

        try {

            // 执行任务
            log.info("任务准备执行，任务ID：{}", scheduleJob.getJobId());

            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(), scheduleJob.getMethodName(), scheduleJob.getParams());

            Future<?> future = service.submit(task);

            future.get();

            Long times = System.currentTimeMillis() - startTime;

            jobLog.setTimes(times);

            // 任务状态 0：成功 1：失败
            jobLog.setStatus(JobLog.JOB_SUCCESS);

            log.info("任务执行完毕，任务ID：{} 总共耗时：{} 毫秒", scheduleJob.getJobId(), times);


        } catch (Exception e) {

            log.error("任务执行失败，任务ID：" + scheduleJob.getJobId(), e);

            long times = System.currentTimeMillis() - startTime;

            jobLog.setTimes(times);

            // 任务状态 0：成功 1：失败
            jobLog.setStatus(JobLog.JOB_FAIL);

            jobLog.setError(StringUtils.substring(e.toString(), 0, 2000));
        } finally {

            jobLogService.save(jobLog);
        }
    }
}
