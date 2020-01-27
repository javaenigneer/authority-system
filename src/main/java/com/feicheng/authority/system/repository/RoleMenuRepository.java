package com.feicheng.authority.system.repository;

import com.feicheng.authority.system.entity.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色菜单Repository
 * @author Lenovo
 */
@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenu, Long>, JpaSpecificationExecutor<RoleMenu> {


    /**
     * 根据MenuId删除角色菜单
     * @param authorityId
     */
    @Transactional
    @Modifying
    @Query("delete from RoleMenu roleMenu where roleMenu.menuId = :authorityId")
    void delete(@Param("authorityId") Long authorityId);

    /**
     * 根据roleId查询角色菜单
     * @param roleId
     * @return
     */
    @Transactional
    @Modifying
    @Query("select roleMenu.menuId from RoleMenu roleMenu where roleMenu.roleId = :roleId" )
    List<Long> select(@Param("roleId") Long roleId);

    /**
     * 根据角色Id
     * @param roleId
     */
    @Transactional
    @Modifying
    @Query("delete from RoleMenu roleMenu where roleMenu.roleId = :roleId")
    void deleteRoleMenuByRoleId(@Param("roleId") Long roleId);

}
