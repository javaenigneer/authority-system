package com.feicheng.authority.job.test;

import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 具体触发任务
 *
 * @author Lenovo
 */
public class HelloScheduler {

    public static void main(String[] args) throws SchedulerException {

        // 创建一个JobDetail的实例，将该实例与HelloJob class绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob").build();

        // 创建一个Trigger触发器的实例，定义该Job立即执行，并且每2秒执行一次，一直执行
        SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger().withIdentity("myTrigger").startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();

        // 创建schedule实例
        StdSchedulerFactory factory = new StdSchedulerFactory();

        Scheduler scheduler = factory.getScheduler();

        scheduler.start();

        scheduler.scheduleJob(jobDetail, simpleTrigger);
    }
}
