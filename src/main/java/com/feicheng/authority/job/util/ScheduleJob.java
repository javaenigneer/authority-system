package com.feicheng.authority.job.util;


import com.feicheng.authority.job.entity.Job;
import com.feicheng.authority.job.entity.JobLog;
import com.feicheng.authority.job.service.JobLogService;
import com.feicheng.authority.job.test.TestData;
import com.feicheng.authority.utils.DateUtils;
import com.feicheng.authority.utils.SpringContextUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext context) {

        Job scheduleJob = (Job) context.getMergedJobDataMap().get(Job.JOB_PARAM_KEY);
        // 查询日期是否在这个范围
        List<TestData> datas = new ArrayList<>();
        TestData testData1 = new TestData();
        testData1.setName("test1");
        testData1.setDate(new Date());
        TestData testData2 = new TestData();
        testData2.setName("test2");
        testData2.setDate(new Date());
        datas.add(testData1);
        datas.add(testData2);
        for (TestData data:datas
             ) {
            // 执行任务
            Long startTime = System.currentTimeMillis();
            log.info("任务准备执行，任务ID：{}", scheduleJob.getJobId());
            log.info("任务信息：{}",data);
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(), scheduleJob.getMethodName(), scheduleJob.getParams());
            Future<?> future = service.submit(task);
            future.get();
            long times = System.currentTimeMillis() - startTime;
            log.info("任务执行完毕，任务ID：{} 总共耗时：{} 毫秒", scheduleJob.getJobId(), times);
        }
    }

//    @Override
//    protected void executeInternal(JobExecutionContext context) {
//
//        Job scheduleJob = (Job) context.getMergedJobDataMap().get(Job.JOB_PARAM_KEY);
//
//        JobLogService jobLogService = SpringContextUtil.getBean(JobLogService.class);
//
//        // 创建日志对象
//        JobLog jobLog = new JobLog();
//
//        // 设置基本参数
//        jobLog.setJobId(scheduleJob.getJobId());
//
//        jobLog.setBeanName(scheduleJob.getBeanName());
//
//        jobLog.setMethodName(scheduleJob.getMethodName());
//
//        jobLog.setParams(scheduleJob.getParams());
//
//        jobLog.setCreateTime(new Date());
//
//        Long startTime = System.currentTimeMillis();
//
//        try {
//
//            // 执行任务
//            log.info("任务准备执行，任务ID：{}", scheduleJob.getJobId());
//
//            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(), scheduleJob.getMethodName(), scheduleJob.getParams());
//
//            Future<?> future = service.submit(task);
//
//            future.get();
//
//            Long times = System.currentTimeMillis() - startTime;
//
//            jobLog.setTimes(times);
//
//            // 任务状态 0：成功 1：失败
//            jobLog.setStatus(JobLog.JOB_SUCCESS);
//
//            log.info("任务执行完毕，任务ID：{} 总共耗时：{} 毫秒", scheduleJob.getJobId(), times);
//
//
//        } catch (Exception e) {
//
//            log.error("任务执行失败，任务ID：" + scheduleJob.getJobId(), e);
//
//            long times = System.currentTimeMillis() - startTime;
//
//            jobLog.setTimes(times);
//
//            // 任务状态 0：成功 1：失败
//            jobLog.setStatus(JobLog.JOB_FAIL);
//
//            jobLog.setError(StringUtils.substring(e.toString(), 0, 2000));
//        } finally {
//
//            jobLogService.save(jobLog);
//        }
//    }
}
