package com.feicheng.authority.system.repository;

import com.feicheng.authority.system.entity.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 管理角色Repository
 * @author Lenovo
 */
@Repository
public interface AdminRoleRepository extends JpaRepository<AdminRole, Long>, JpaSpecificationExecutor<AdminRole> {

    /**
     * 根据角色Id删除管理员角色
     */

    @Transactional
    @Modifying
    @Query("delete from AdminRole adminRole where adminRole.roleId = :roleId")
    void deleteByRoleId(@Param(("roleId")) Long roleId);
}
