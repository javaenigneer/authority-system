package com.feicheng.authority.system.service;

import com.feicheng.authority.common.response.DeptTree;
import com.feicheng.authority.common.response.MenuTree;
import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.system.entity.Dept;

/**
 * @author Lenovo
 */
public interface DeptService {

    // 获取部门节点树的数据
    DeptTree<Dept> selectNodes();

    // 添加部门
    ResponseResult<Void> add(String deptName, Long parentId);

    // 修改部门
    ResponseResult<Void> edit(String deptName, Long deptId);

    // 删除部门
    ResponseResult<Void> deleteDept(Long deptId);

}
