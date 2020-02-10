package com.feicheng.authority.job.test;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 具体要实现的任务
 * @author Lenovo
 */
public class HelloJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {


        Date date = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println("现在的时间是：" + simpleDateFormat.format(date));

        // 具体实现的业务逻辑
        System.out.println("Hello Quartz");
    }

}
