package com.feicheng.authority.system.repository;

import com.feicheng.authority.system.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Lenovo
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {

    @Transactional
    @Modifying
    @Query("delete from Menu menu where menu.parentId = :authorityId")
    void deleteMenuByParentId(@Param("authorityId") Long authorityId);
}
