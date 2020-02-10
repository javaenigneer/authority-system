package com.feicheng.authority.job.repository;

import com.feicheng.authority.job.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 任务调度Repository
 * @author Lenovo
 */
@Repository
public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {

    // 批量删除任务调度
    @Transactional
    @Modifying
    @Query("delete from Job job where job.jobId in (:ids)")
    void deleteIds(@Param("ids") List<Long> ids);
}
