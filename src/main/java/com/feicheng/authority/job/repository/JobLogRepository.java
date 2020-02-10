package com.feicheng.authority.job.repository;

import com.feicheng.authority.job.entity.JobLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 任务调度日志Repository
 * @author Lenovo
 */
@Repository
public interface JobLogRepository extends JpaRepository<JobLog, Long>, JpaSpecificationExecutor<JobLog> {

}
