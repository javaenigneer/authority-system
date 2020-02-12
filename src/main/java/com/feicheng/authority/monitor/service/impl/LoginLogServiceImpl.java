package com.feicheng.authority.monitor.service.impl;

import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.job.entity.JobLog;
import com.feicheng.authority.monitor.entity.LoginLog;
import com.feicheng.authority.monitor.repository.LoginLogRepository;
import com.feicheng.authority.monitor.service.LoginLogService;
import com.feicheng.authority.utils.*;
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
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 登录日志Service
 *
 * @author Lenovo
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {


    @Autowired(required = false)
    private LoginLogRepository loginLogRepository;


    /**
     * 添加登录日志
     *
     * @param loginLog
     */
    @Override
    public void add(LoginLog loginLog) {

        // 设置基本参数
        loginLog.setLoginTime(new Date());

        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();

        // 获取登录的IP
        String ipAddr = IPUtil.getIpAddr(request);

        loginLog.setIp(ipAddr);

        loginLog.setLocation(AddressUtil.getCityInfo(ipAddr));

        try {

            this.loginLogRepository.saveAndFlush(loginLog);


        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 分页查询全部的登录日志
     *
     * @param page
     * @param limit
     * @param start
     * @param end
     * @param userName
     * @return
     */
    @Override
    public ResponseResult<LoginLog> list(Integer page, Integer limit, String start, String end, String userName) {

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.Direction.DESC, "id");

        Specification<LoginLog> specification = new Specification<LoginLog>() {

            List<Predicate> list = new ArrayList<>();

            @Override
            public Predicate toPredicate(Root<LoginLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                // 判断查询条件

                // userName
                if (StringUtils.isNotBlank(userName)) {

                    list.add(criteriaBuilder.like(root.get("userName").as(String.class), "%" + userName + "%"));
                }

                // start
                if (StringUtils.isNotBlank(start)) {

                    Date startDate = DateUtils.parseString(start, "yyyy-MM-dd");

                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("loginTime").as(Date.class), startDate));
                }

                // end
                if (StringUtils.isNotBlank(end)) {

                    Date endDate = DateUtils.parseString(end, "yyyy-MM-dd");

                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("loginTime").as(Date.class), endDate));
                }

                Predicate[] predicates = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(predicates));
            }
        };

        // 执行查询
        Page<LoginLog> loginLogPage = this.loginLogRepository.findAll(specification, pageable);

        // 判断是否有数据
        if (CollectionUtils.isEmpty(loginLogPage.getContent())) {

            return new ResponseResult<>(0, "查询成功", null, 0L);
        }

        // 有数据
        return new ResponseResult<>(0, "查询成功", loginLogPage.getContent(), loginLogPage.getTotalElements());
    }


    /**
     * 删除登录日志
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult<Void> delete(Long id) {

        // 判断参数是否合格
        if (id == null) {

            return new ResponseResult<>(400, "参数错误");
        }

        try {
            // 执行删除
            this.loginLogRepository.deleteById(id);

            return new ResponseResult<>(200, "删除成功");
        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseResult<>(500, "删除失败");
        }
    }

    /**
     * 批量删除登录日志
     *
     * @param ids
     * @return
     */
    @Override
    public ResponseResult<Void> deleteIds(String ids) {

        // 判断参数是否合格
        if (StringUtils.isBlank(ids)) {

            return new ResponseResult<>(400, "参数错误");
        }

        try {
            // 执行删除
            // 转换类型
            List<Long> loginLogIds = StringUtil.stingConvertLong(ids);

            // 执行删除
            this.loginLogRepository.deleteIds(loginLogIds);

            return new ResponseResult<>(200, "删除成功");

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseResult<>(500, "删除失败");
        }
    }
}
