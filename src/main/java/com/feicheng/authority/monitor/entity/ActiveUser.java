package com.feicheng.authority.monitor.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 在线用户
 * @author Lenovo
 */
@Data
public class ActiveUser implements Serializable {

    /**
     * Session Id
     */
    private String id;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户主机地址
     */
    private String host;

    /**
     * 用户登录时系统 IP
     */
    private String systemHost;

    /**
     * 状态
     */
    private String status;

    /**
     * Session创建时间
     */
    private String startTimestamp;

    /**
     * Session最后访问时间
     */
    private String lastAccessTime;

    /**
     * 超时时间
     */
    private Long timeOut;

    /**
     * 所在地
     */
    private String location;

    /**
     * 是否判断当前用户
     */
    private boolean current;
}
