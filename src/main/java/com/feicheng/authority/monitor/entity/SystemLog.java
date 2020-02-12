package com.feicheng.authority.monitor.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 系统日志
 * @author Lenovo
 */
@Table(name = "t_log")
@Data
@Entity
public class SystemLog {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String operation;

    private Long time;

    private String method;

    private String params;

    private String ip;

    private Date createTime;

    private String location;


}
