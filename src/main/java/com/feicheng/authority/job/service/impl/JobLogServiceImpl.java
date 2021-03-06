package com.feicheng.authority.job.service.impl;

import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.job.entity.JobLog;
import com.feicheng.authority.job.repository.JobLogRepository;
import com.feicheng.authority.job.service.JobLogService;
import com.feicheng.authority.utils.DateUtils;
import com.feicheng.authority.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

/**
 * JobLogService
 *
 * @author Lenovo
 */
@Service
public class JobLogServiceImpl implements JobLogService {


    @Autowired(required = false)
    private JobLogRepository jobLogRepository;

    /**
     * 保存任务调度日志
     *
     * @param jobLog
     */
    @Override
    public void save(JobLog jobLog) {

        try {

            this.jobLogRepository.saveAndFlush(jobLog);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 分页查询全部的日志信息
     *
     * @param page
     * @param limit
     * @param start
     * @param end
     * @param beanName
     * @param status
     * @return
     */
    @Override
    public ResponseResult<JobLog> list(Integer page, Integer limit, String start, String end, String beanName, Integer status) {

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.Direction.DESC, "jobId");

        Specification<JobLog> specification = new Specification<JobLog>() {

            List<Predicate> list = new ArrayList<>();

            @Override
            public Predicate toPredicate(Root<JobLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                // 判断查询条件

                // beanName
                if (StringUtils.isNotBlank(beanName)) {

                    list.add(criteriaBuilder.like(root.get("beanName").as(String.class), "%" + beanName + "%"));
                }

                // status
                if (status != null) {

                    list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), status));
                }

                // start
                if (StringUtils.isNotBlank(start)) {

                    Date startDate = DateUtils.parseString(start, "yyyy-MM-dd");

                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Date.class), startDate));
                }

                // end
                if (StringUtils.isNotBlank(end)) {

                    Date endDate = DateUtils.parseString(end, "yyyy-MM-dd");

                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(Date.class), endDate));
                }

                Predicate[] predicates = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(predicates));
            }
        };

        // 执行查询
        Page<JobLog> jobLogPage = this.jobLogRepository.findAll(specification, pageable);

        // 判断是否有数据
        if (CollectionUtils.isEmpty(jobLogPage.getContent())) {

            return new ResponseResult<>(0, "查询成功", null, 0L);
        }

        // 有数据
        return new ResponseResult<>(0, "查询成功", jobLogPage.getContent(), jobLogPage.getTotalElements());
    }

    /**
     * 删除任务调度日志
     *
     * @param jobLogId
     * @return
     */
    @Override
    public ResponseResult<Void> delete(Long jobLogId) {

        // 判断参数
        if (jobLogId == null) {

            return new ResponseResult<>(400, "参数错误");
        }

        try {
            // 执行删除
            this.jobLogRepository.deleteById(jobLogId);

            return new ResponseResult<>(200, "删除成功");

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseResult<>(500, "删除失败");
        }
    }

    /**
     * 批量删除任务调度日志
     *
     * @param jobLogIds
     * @return
     */
    @Override
    public ResponseResult<Void> deleteIds(String jobLogIds) {

        // 判断参数是否合格
        if (StringUtils.isBlank(jobLogIds)) {

            return new ResponseResult<>(400, "参数错误");
        }

        // 将String装成List<Long>
        try {

            List<Long> ids = StringUtil.stingConvertLong(jobLogIds);

            // 执行删除
            this.jobLogRepository.deleteIds(ids);

            return new ResponseResult<>(200, "删除成功");

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseResult<>(500, "删除失败");
        }
    }
}
