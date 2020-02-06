package com.feicheng.authority.system.service.impl;

import com.feicheng.authority.common.response.DeptTree;
import com.feicheng.authority.common.response.MenuTree;
import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.system.entity.Dept;
import com.feicheng.authority.system.repository.DeptRepository;
import com.feicheng.authority.system.service.DeptService;
import com.feicheng.authority.utils.TreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * DeptService
 *
 * @author Lenovo
 */
@Service
public class DeptServiceImpl implements DeptService {


    @Autowired(required = false)
    private DeptRepository deptRepository;


    /**
     * 获取部门节点树的数据
     *
     * @return
     */
    @Override
    public DeptTree<Dept> selectNodes() {

        // 获取所有的数据
        List<Dept> depts = this.deptRepository.findAll();

        // 转换成节点树
        List<DeptTree<Dept>> deptTrees = this.covertMenus(depts);

        // 返回构建好的节点树
        return TreeUtil.buildDeptTree(deptTrees);
    }

    /**
     * 添加部门
     *
     * @param deptName
     * @param parentId
     * @return
     */
    @Override
    public ResponseResult<Void> add(String deptName, Long parentId) {

        // 判断参数是否合格
        if (StringUtils.isBlank(deptName) || parentId == null || StringUtils.isBlank(parentId.toString())) {

            return new ResponseResult<>(400, "参数错误");
        }
        // 创建一个Dept对象
        Dept dept = new Dept();

        // 设置参数
        dept.setDeptName(deptName);

        dept.setParentId(parentId);

        dept.setCreateTime(new Date());

        dept.setModifyTime(dept.getCreateTime());

        dept.setOrderNum(1L);

        // 执行添加
        try {

            this.deptRepository.saveAndFlush(dept);

            return new ResponseResult<>(200, "添加成功");

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseResult<>(500, "添加失败");

        }
    }

    /**
     * 修改部门
     *
     * @param deptName
     * @param deptId
     * @return
     */
    @Override
    public ResponseResult<Void> edit(String deptName, Long deptId) {

        // 判断参数是否合格
        if (StringUtils.isBlank(deptName) || deptId == null || StringUtils.isBlank(deptId.toString())) {

            return new ResponseResult<>(400, "参数错误");
        }

        try {

            // 根据Id查询部门
            Optional<Dept> optionalDept = this.deptRepository.findById(deptId);

            Dept dept = optionalDept.get();

            if (dept == null) {

                return new ResponseResult<>(400, "没有改部门");

            }

            // 修改部门名称
            dept.setDeptName(deptName);

            // 执行修改
            this.deptRepository.saveAndFlush(dept);

            return new ResponseResult<>(200, "修改成功");

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseResult<>(500, "修改失败");
        }
    }

    /**
     * 删除部门
     *
     * @param deptId
     * @return
     */
    @Override
    public ResponseResult<Void> deleteDept(Long deptId) {

        // 判断参数是否合格
        if (deptId == null || StringUtils.isBlank(deptId.toString())) {

            return new ResponseResult<>(400, "参数错误");

        }

        try {
            // 先根据deptId查询是否有下级部门
            Specification<Dept> specification = new Specification<Dept>() {

                List<Predicate> list = new ArrayList<>();

                @Override
                public Predicate toPredicate(Root<Dept> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                    list.add(criteriaBuilder.equal(root.get("parentId").as(String.class), deptId));

                    Predicate[] predicates = new Predicate[list.size()];

                    return criteriaBuilder.and(list.toArray(predicates));
                }
            };

            // 执行查询
            List<Dept> depts = this.deptRepository.findAll(specification);

            // 没有下级部门
            if (CollectionUtils.isEmpty(depts)) {

                // 直接删除该级部门
                // 创建部门
                Dept dept = new Dept();

                dept.setDeptId(deptId);

                // 执行删除
                this.deptRepository.delete(dept);

                return new ResponseResult<>(200, "删除成功");

            }


            // 有下级部门

            // 先删除下级部门
            this.deptRepository.deleteDeptByParentId(deptId);

            // 再删除该级部门
            Dept dept = new Dept();

            dept.setDeptId(deptId);

            // 执行删除
            this.deptRepository.delete(dept);

            return new ResponseResult<>(200, "删除成功");

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseResult<>(500, "删除失败");
        }

    }


    /**
     * 转化成节点树
     *
     * @param depts
     * @return
     */
    private List<DeptTree<Dept>> covertMenus(List<Dept> depts) {

        List<DeptTree<Dept>> trees = new ArrayList<>();

        depts.forEach(dept -> {

            DeptTree<Dept> tree = new DeptTree<>();

            tree.setId(String.valueOf(dept.getDeptId()));

            tree.setParentId(String.valueOf(dept.getParentId()));

            tree.setTitle(dept.getDeptName());

            tree.setData(dept);

            trees.add(tree);
        });

        return trees;
    }
}
