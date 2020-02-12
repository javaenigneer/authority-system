package com.feicheng.authority.monitor.repository;

import com.feicheng.authority.monitor.entity.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 登录日志Repository
 * @author Lenovo
 */
@Repository
public interface LoginLogRepository extends JpaRepository<LoginLog, Long>, JpaSpecificationExecutor<LoginLog> {

    /**
     * 批量删除
     * @param loginLogIds
     */
    @Transactional
    @Modifying
    @Query("delete from LoginLog loginLog where id in (:loginLogIds)")
    void deleteIds(@Param("loginLogIds") List<Long> loginLogIds);
}
