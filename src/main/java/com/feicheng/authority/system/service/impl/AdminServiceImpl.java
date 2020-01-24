package com.feicheng.authority.system.service.impl;

import com.feicheng.authority.utils.StringUtil;
import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.system.entity.Admin;
import com.feicheng.authority.system.repository.AdminRepository;
import com.feicheng.authority.system.service.AdminService;
import com.feicheng.authority.utils.DateUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.xml.crypto.Data;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 管理员Service
 *
 * @author Lenovo
 */
@Service
public class AdminServiceImpl implements AdminService {


    @Autowired(required = false)
    private AdminRepository adminRepository;

    // 判断邮箱格式
    private String emailRegex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    // 判断手机号码格式
    private String phoneRegex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";

    /**
     * 查询所有的管理员
     *
     * @return
     */
    @Override
    public ResponseResult<Admin> list(Integer page, Integer limit, String start, String end, String adminName, String adminEmail, String adminPhone) {


        Pageable pageable = PageRequest.of(page - 1, limit, Sort.Direction.DESC, "adminId");


        Specification<Admin> specification = new Specification<Admin>() {

            @Override
            public Predicate toPredicate(Root<Admin> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<>();


                // 设置了管理员查询名称
                if (StringUtils.isNotBlank(adminName)) {

                    list.add(criteriaBuilder.like(root.get("adminName").as(String.class), "%" + adminName + "%"));
                }

                // 设置了管理员查询邮箱
                if (StringUtils.isNotBlank(adminEmail)) {

                    list.add(criteriaBuilder.like(root.get("adminEmail").as(String.class), "%" + adminEmail + "%"));

                }

                // 设置了管理员查询手机号码
                if (StringUtils.isNotBlank(adminPhone)) {

                    list.add(criteriaBuilder.like(root.get("adminPhone").as(String.class), "%" + adminPhone + "%"));

                }

                // 设置了管理员加入时间
                if (StringUtils.isNotBlank(start)) {

                    Date adminStartTime = DateUtils.parseString(start, "yyyy-MM-dd");

                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("adminCreateTime").as(Date.class), adminStartTime));
                }

                // 设置了管理员加入时间
                if (StringUtils.isNotBlank(end)) {

                    Date adminEndTime = DateUtils.parseString(end, "yyyy-MM-dd");

                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("adminCreateTime").as(Date.class), adminEndTime));
                }

                // 设置查询条件
                list.add(criteriaBuilder.equal(root.get("adminDelete").as(Integer.class), 1));

                Predicate[] predicates = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(predicates));
            }
        };

        Page<Admin> pageResult = this.adminRepository.findAll(specification, pageable);

        // 若没有数据
        if (CollectionUtils.isEmpty(pageResult.getContent())) {

            return new ResponseResult<>(0, "查询成功", null, 0L);
        }

        return new ResponseResult<>(0, "查询成功", pageResult.getContent(), (long) pageResult.getSize());
    }

    /**
     * 修改管理员
     *
     * @param admin
     * @return
     */
    @Override
    @Transactional
    public ResponseResult<Void> edit(Admin admin) {

        // 无Id
        if (StringUtils.isBlank(admin.getAdminId().toString())) {

            return new ResponseResult<>(400, "参数错误");
        }

        // 无名称
        if (StringUtils.isBlank(admin.getAdminName())) {

            return new ResponseResult<>(400, "请输入名称");
        }

        // 无邮箱
        if (StringUtils.isBlank(admin.getAdminEmail())) {

            return new ResponseResult<>(400, "请输入邮箱");
        }

        // 邮箱格式错误
        if (!Pattern.matches(emailRegex, admin.getAdminEmail())) {

            return new ResponseResult<>(400, "邮箱格式错误");
        }

        // 手机号码
        if (StringUtils.isBlank(admin.getAdminPhone())) {

            return new ResponseResult<>(400, "请输入手机号码");
        }

        // 手机号码格式
        if (!Pattern.matches(phoneRegex, admin.getAdminPhone())) {

            return new ResponseResult<>(400, "手机号码格式错误");
        }

        // 设置基本信息
        admin.setAdminCreateTime(new Date());

        admin.setAdminUpdateTime(admin.getAdminCreateTime());

        admin.setAdminDelete(1);

        admin.setAdminStatus(1);

        try {

            // 执行修改
            this.adminRepository.saveAndFlush(admin);

            return new ResponseResult<>(200, "修改成功");

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseResult<>(500, "修改失败");
        }
    }

    /**
     * 删除管理员
     *
     * @param adminId
     * @return
     */
    @Override
    public ResponseResult<Void> delete(Long adminId) {

        // 判断id是否为空
        if (adminId == null) {

            return new ResponseResult<>(400, "参数错误");
        }

        // 根据id查询管理员
        Optional<Admin> optionalAdmin = this.adminRepository.findById(adminId);

        Admin admin = optionalAdmin.get();

        if (admin == null) {

            return new ResponseResult<>(400, "没有该用户");
        }

        // 将用户的delete修改为0
        admin.setAdminDelete(0);

        try {

            // 执行删除
            this.adminRepository.saveAndFlush(admin);

            return new ResponseResult<>(200, "删除成功");

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseResult<>(500, "删除失败");
        }
    }

    /**
     * 批量删除管理员
     *
     * @param adminIds
     * @return
     */
    @Override
    public ResponseResult<Void> deleteIds(String adminIds) {

        // 判断是否为空
        if (StringUtils.isBlank(adminIds)) {

            return new ResponseResult<>(400, "参数错误");
        }

        try {

            List<Long> ids = StringUtil.stingConvertLong(adminIds);

            this.adminRepository.deleteIds(ids);

            return new ResponseResult<>(200, "删除成功");

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseResult<>(500, "删除失败");
        }

    }
}
