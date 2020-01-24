package com.feicheng.authority.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 管理员类
 * @author Lenovo
 */
@Table(name = "t_admin")
@Data
@Entity
public class Admin {

    /**
     * 管理员Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long adminId;


    /**
     * 管理员名称
     */
    private String adminName;


    /**
     * 管理员密码
     */
    private String adminPassword;


    /**
     * 管理员邮箱
     */
    private String adminEmail;


    /**
     * 管理员性别
     */
    private char adminSex;


    /**
     * 管理员手机号
     */
    private String adminPhone;


    /**
     * 管理员状态
     */
    private Integer adminStatus;


    /**
     * 管理员删除标志
     */
    private Integer adminDelete;


    /**
     * 管理员创建时间
     */
    private Date adminCreateTime;


    /**
     * 管理员更新时间
     */
    private Date adminUpdateTime;


    /**
     * 密码确认
     */
    @Transient
    private String againPassword;


}
