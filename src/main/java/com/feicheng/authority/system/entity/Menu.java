package com.feicheng.authority.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 菜单类
 * @author Lenovo
 */
@Table(name = "t_menu")
@Entity
@Data
public class Menu {

    /**
     *菜单/按钮ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorityId;


    /**
     *上级菜单
     */
    private Long parentId;

    /**
     * 菜单/按钮名称
     */
    private String authorityName;


    /**
     * 菜单url
     */
    private String menuUrl;


    /**
     * 权限标识
     */
    private String authority;


    private Integer checked;

    /**
     * 图标
     */
    private String menuIcon;

    /**
     * 类型 0菜单 1按钮
     */
    private String isMenu;

    /**
     * 排序
     */
    private Long orderNumber;


    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 修改时间
     */
    private Date updateTime;
}
