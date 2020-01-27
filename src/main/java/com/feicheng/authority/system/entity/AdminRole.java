package com.feicheng.authority.system.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 管理员角色类
 * @author Lenovo
 */
@Table(name = "t_admin_role")
@Entity
@Data
public class AdminRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminRoleId;

    private Long adminId;

    private Long roleId;

}
