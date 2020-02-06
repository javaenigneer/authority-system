package com.feicheng.authority.system.repository;

import com.feicheng.authority.system.entity.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Dept操作数据库
 * @author Lenovo
 */
@Repository
public interface DeptRepository extends JpaRepository<Dept, Long>, JpaSpecificationExecutor<Dept> {

    @Transactional
    @Modifying
    @Query("delete from Dept dept where dept.parentId = :parentId")
    void deleteDeptByParentId(@Param("parentId") Long deptId);
}

