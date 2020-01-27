package com.feicheng.authority.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 角色类
 * @author Lenovo
 */
@Table(name = "t_role")
@Entity
@Data
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    private String roleName;

    private String remark;

    private Date createTime;

    private Date modifyTime;

    // 非数据库字段
    @Transient
    private String RoleMenu;

    public String getRoleMenu() {
        return RoleMenu;
    }

    public void setRoleMenu(String roleMenu) {
        RoleMenu = roleMenu;
    }
}
