package com.feicheng.authority.system.repository;

import com.feicheng.authority.system.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * admin数据操作
 * @author Lenovo
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>, JpaSpecificationExecutor<Admin> {

    /**
     * 批量删除
     * @param adminIds
     */
    @Transactional
    @Modifying
    @Query("update Admin a set a.adminDelete = 0 where a.adminId in (:adminIds)")
    void deleteIds(@Param("adminIds")List<Long> adminIds);
}
