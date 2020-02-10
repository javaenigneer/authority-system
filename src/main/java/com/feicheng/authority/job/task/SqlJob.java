package com.feicheng.authority.job.task;
import	java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Sql任务调度
 * @author Lenovo
 */
public class SqlJob implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobDetail jobDetail = jobExecutionContext.getJobDetail();

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        String format = dateFormat.format(new Date());

        System.out.printf("发送一封邮件，当前时间是：%s%n", format);

        try {

            Thread.sleep(10000);

        }catch (InterruptedException e){

            e.printStackTrace();
        }
    }
}
