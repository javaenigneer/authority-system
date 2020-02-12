package com.feicheng.authority.job.service.impl;

import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.job.entity.Job;
import com.feicheng.authority.job.repository.JobRepository;
import com.feicheng.authority.job.service.JobService;
import com.feicheng.authority.job.task.SqlJob;
import com.feicheng.authority.job.util.ScheduleUtils;
import com.feicheng.authority.utils.DateUtils;
import com.feicheng.authority.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 任务调度Service
 *
 * @author Lenovo
 */
@Service
public class JobServiceImpl implements JobService {


    @Autowired(required = false)
    private JobRepository jobRepository;

    @Autowired(required = false)
    private Scheduler scheduler;

    @PostConstruct
    public void init() {


        // 查询全部的任务调度
        List<Job> jobs = this.jobRepository.findAll();

        // 如果不存在则创建
        jobs.forEach(job -> {

            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, job.getJobId());
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, job);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, job);
            }
        });

    }

    /**
     * 分页查询全部的任务调度
     *
     * @param page
     * @param limit
     * @return
     */
    @Override
    public ResponseResult<Job> list(Integer page, Integer limit, String start, String end, String beanName, Integer status) {

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.Direction.DESC, "jobId");

        Specification<Job> specification = new Specification<Job>() {

            List<Predicate> list = new ArrayList<>();

            @Override
            public Predicate toPredicate(Root<Job> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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
        Page<Job> jobPage = this.jobRepository.findAll(specification, pageable);

        // 判断是否有数据
        if (CollectionUtils.isEmpty(jobPage.getContent())) {

            return new ResponseResult<>(0, "查询成功", null, 0L);
        }

        // 有数据
        return new ResponseResult<>(0, "查询成功", jobPage.getContent(), (long) jobPage.getTotalElements());
    }

    /**
     * 恢复任务
     *
     * @param jobId
     * @return
     */
    @Override
    public ResponseResult<Void> resume(Long jobId) {

        // 判断参数
        if (jobId == null) {

            return new ResponseResult<>(400, "参数错误");
        }

        try {

            // 根据id查询任务
            Optional<Job> optionalJob = this.jobRepository.findById(jobId);

            Job job = optionalJob.get();

            if (job == null) {

                return new ResponseResult<>(400, "没有该任务");

            }

            // 修改状态
            job.setStatus("1");

            // 执行修改
            this.jobRepository.saveAndFlush(job);

            ScheduleUtils.resumeJob(scheduler, job.getJobId());

            return new ResponseResult<>(200, "启动成功");

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseResult<>(500, "启动失败");
        }
    }

    /**
     * 暂停任务
     *
     * @param jobId
     * @return
     */
    @Override
    public ResponseResult<Void> end(Long jobId) {

        // 判断参数合格
        if (jobId == null) {

            return new ResponseResult<>(400, "参数错误");
        }

        try {

            // 根据Id查询任务
            Optional<Job> optionalJob = this.jobRepository.findById(jobId);

            Job job = optionalJob.get();

            if (job == null) {

                return new ResponseResult<>(400, "没有改任务");
            }

            // 修改状态
            job.setStatus("0");

            // 执行修改
            this.jobRepository.saveAndFlush(job);

            ScheduleUtils.pauseJob(scheduler, job.getJobId());

            return new ResponseResult<>(200, "暂停成功");

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseResult<>(500, "暂停失败");
        }
    }

    /**
     * 编辑任务
     *
     * @param job
     * @return
     */
    @Override
    public ResponseResult<Void> edit(Job job) {

        // 判断参数是否合格

        // Id为空
        if (job.getJobId() == null) {

            return new ResponseResult<>(400, "参数错误");
        }

        // Bean名称为空
        if (StringUtils.isBlank(job.getBeanName())) {

            return new ResponseResult<>(400, "请输入BeanName");
        }

        // 方法名称为空
        if (StringUtils.isBlank(job.getMethodName())) {

            return new ResponseResult<>(400, "请输入方法名称");
        }

        // 方法参数为空
        if (StringUtils.isBlank(job.getParams())) {

            return new ResponseResult<>(400, "请输入方法参数");
        }

        // Cron表达式为空
        if (StringUtils.isBlank(job.getCronExpression())) {

            return new ResponseResult<>(400, "请输入Cron表达式");
        }

        // 任务备注为空
        if (StringUtils.isBlank(job.getRemark())) {

            return new ResponseResult<>(400, "请输入任务备注");
        }

        // 参数全部通过
        try {
            // 根据Id查询任务
            Optional<Job> optionalJob = this.jobRepository.findById(job.getJobId());

            Job jobSql = optionalJob.get();

            if (jobSql == null) {

                return new ResponseResult<>(400, "没有该任务");
            }

            // 修改参数
            jobSql.setBeanName(job.getBeanName());

            jobSql.setMethodName(job.getMethodName());

            jobSql.setParams(job.getParams());

            jobSql.setCronExpression(job.getCronExpression());

            jobSql.setRemark(job.getRemark());

            // 执行修改
            this.jobRepository.saveAndFlush(jobSql);

            return new ResponseResult<>(200, "修改成功");

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseResult<>(500, "修改失败");
        }
    }

    /**
     * 添加任务调度
     *
     * @param job
     * @return
     */
    @Override
    public ResponseResult<Void> add(Job job) {

        // 判断参数是否合格

        // Bean名称为空
        if (StringUtils.isBlank(job.getBeanName())) {

            return new ResponseResult<>(400, "请输入BeanName");
        }

        // 方法名称为空
        if (StringUtils.isBlank(job.getMethodName())) {

            return new ResponseResult<>(400, "请输入方法名称");
        }

        // 方法参数为空
        if (StringUtils.isBlank(job.getParams())) {

            return new ResponseResult<>(400, "请输入方法参数");
        }

        // Cron表达式为空
        if (StringUtils.isBlank(job.getCronExpression())) {

            return new ResponseResult<>(400, "请输入Cron表达式");
        }

        // 任务备注为空
        if (StringUtils.isBlank(job.getRemark())) {

            return new ResponseResult<>(400, "请输入任务备注");
        }

        // 参数全部通过
        // 设置基本参数

        // 设置状态为暂停
        job.setStatus("0");

        job.setCreateTime(new Date());

        try {

            // 实现添加
            this.jobRepository.saveAndFlush(job);

            ScheduleUtils.createScheduleJob(scheduler, job);

            return new ResponseResult<>(200, "添加成功");

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseResult<>(500, "添加失败");

        }
    }

    /**
     * 删除任务调度
     *
     * @return
     */
    @Override
    public ResponseResult<Void> delete(Long jobId) {

        // 判断参数
        if (jobId == null) {

            return new ResponseResult<>(400, "参数错误");
        }

        try {

            // 执行删除
            this.jobRepository.deleteById(jobId);

            ScheduleUtils.deleteScheduleJob(scheduler, jobId);

            return new ResponseResult<>(200, "删除成功");

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseResult<>(500, "删除失败");
        }
    }

    /**
     * 删除部分任务调度
     *
     * @param jobIds
     * @return
     */
    @Override
    public ResponseResult<Void> deleteIds(String jobIds) {

        // 判断参数是否为空
        if (StringUtils.isBlank(jobIds)) {

            return new ResponseResult<>(400, "请至少选择一条数据");
        }

        try {

            List<Long> ids = StringUtil.stingConvertLong(jobIds);

            ids.forEach(id -> {

                ScheduleUtils.deleteScheduleJob(scheduler, id);
            });

            this.jobRepository.deleteIds(ids);

            return new ResponseResult<>(200, "删除成功");

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseResult<>(500, "删除失败");
        }
    }

    /**
     * 执行任务
     *
     * @param jobIds
     * @return
     */
    @Override
    public ResponseResult<Void> start(String jobIds) {

        // 判断参数是否为空
        if (StringUtils.isBlank(jobIds)) {

            return new ResponseResult<>(400, "请选择一条数据");
        }

        try {

            List<Long> ids = StringUtil.stingConvertLong(jobIds);

            ids.forEach(id -> {

                // 根据id查询任务
                Optional<Job> optionalJob = this.jobRepository.findById(id);

                Job job = optionalJob.get();

                ScheduleUtils.run(scheduler, job);
            });

            return new ResponseResult<>(200, "执行成功");

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseResult<>(500, "执行失败");
        }
    }
}
