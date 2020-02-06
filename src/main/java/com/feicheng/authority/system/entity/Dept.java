package com.feicheng.authority.system.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 部门类
 * @author Lenovo
 */
@Table(name = "t_dept")
@Entity
@Data
public class Dept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deptId;

    private Long parentId;

    private String deptName;

    private Long orderNum;

    private Date createTime;

    private Date modifyTime;


}
