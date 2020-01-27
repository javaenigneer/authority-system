package com.feicheng.authority.system.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 角色菜单类
 * @author Lenovo
 */
@Table(name = "t_role_menu")
@Entity
@Data
public class RoleMenu {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleMenuId;

    private Long roleId;

    private Long menuId;


}
