package com.feicheng.authority.job.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 任务调度日志信息
 * @author Lenovo
 */
@Table(name = "t_job_log")
@Entity
@Data
public class JobLog implements Serializable {


    // 任务执行成功
    public static final String JOB_SUCCESS = "0";
    // 任务执行失败
    public static final String JOB_FAIL = "1";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    private Long jobId;

    private String beanName;

    private String methodName;

    private String params;

    private String status;

    private String error;

    private Long times;

    private Date createTime;

}
