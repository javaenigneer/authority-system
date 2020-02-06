package com.feicheng.authority.system.controller.dept;

import com.feicheng.authority.common.response.DeptTree;
import com.feicheng.authority.common.response.MenuTree;
import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.system.entity.Dept;
import com.feicheng.authority.system.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 部门管理Controller
 *
 * @author Lenovo
 */
@Controller
@RequestMapping("dept")
public class DeptController {


    @Autowired
    private DeptService deptService;

    /**
     * 获取部门节点树的数据
     *
     * @return
     */
    @GetMapping("tree")
    public ResponseEntity<List<DeptTree<Dept>>> selectNodes() {


        DeptTree<Dept> deptTree = this.deptService.selectNodes();

        return ResponseEntity.ok(deptTree.getChildren());
    }


    /**
     * 添加部门
     *
     * @return
     */
    @PostMapping("add")
    public ResponseEntity<ResponseResult<Void>> addDept(@RequestParam("deptName") String deptName,
                                                        @RequestParam("parentId") Long parentId) {

        ResponseResult<Void> responseResult = this.deptService.add(deptName, parentId);

        return ResponseEntity.ok(responseResult);
    }

    /**
     * 修改部门
     *
     * @param deptName
     * @param deptId
     * @return
     */
    @PostMapping("edit")
    public ResponseEntity<ResponseResult<Void>> editDept(@RequestParam("deptName") String deptName,
                                                         @RequestParam("deptId") Long deptId) {

        ResponseResult<Void> responseResult = this.deptService.edit(deptName, deptId);

        return ResponseEntity.ok(responseResult);
    }


    /**
     * 删除部门
     * @param deptId
     * @return
     */
    @PostMapping("delete")
    public ResponseEntity<ResponseResult<Void>> delete(@RequestParam("deptId") Long deptId) {

        ResponseResult<Void> responseResult = this.deptService.deleteDept(deptId);

        return ResponseEntity.ok(responseResult);
    }

}
