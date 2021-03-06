package com.feicheng.authority.job.repository;

import com.feicheng.authority.job.entity.JobLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 任务调度日志Repository
 * @author Lenovo
 */
@Repository
public interface JobLogRepository extends JpaRepository<JobLog, Long>, JpaSpecificationExecutor<JobLog> {

    @Transactional
    @Modifying
    @Query("delete from JobLog jobLog where jobLog.logId in (:jobLogIds)")
    void deleteIds(@Param("jobLogIds") List<Long> jobLogIds);
}
