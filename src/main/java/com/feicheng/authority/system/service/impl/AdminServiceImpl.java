package com.feicheng.authority.system.service.impl;


import com.feicheng.authority.common.response.MessageResult;
import com.feicheng.authority.system.entity.AdminRole;

import com.feicheng.authority.system.entity.Menu;
import com.feicheng.authority.system.entity.RoleMenu;
import com.feicheng.authority.system.repository.AdminRoleRepository;
import com.feicheng.authority.system.repository.MenuRepository;
import com.feicheng.authority.system.repository.RoleMenuRepository;
import com.feicheng.authority.system.service.LoginService;
import com.feicheng.authority.system.service.MailService;
import com.feicheng.authority.utils.RandomValidateCode;
import com.feicheng.authority.utils.StringUtil;
import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.system.entity.Admin;
import com.feicheng.authority.system.repository.AdminRepository;
import com.feicheng.authority.system.service.AdminService;
import com.feicheng.authority.utils.DateUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
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

    @Autowired(required = false)
    private AdminRoleRepository adminRoleRepository;

    @Autowired(required = false)
    private MenuRepository menuRepository;

    @Autowired(required = false)
    private RoleMenuRepository roleMenuRepository;

    @Autowired(required = false)
    private LoginService loginService;

    @Autowired(required = false)
    private MailService mailService;

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

    /**
     * 登录操作
     *
     * @param adminName
     * @param adminPassword
     * @param captcha
     * @param request
     * @return
     */
    @Override
    public ResponseResult<Void> login(String adminName, String adminPassword, String captcha, HttpServletRequest request) {

        Subject subject = SecurityUtils.getSubject();

        // 判断参数

        // 没有登录名
        if (StringUtils.isBlank(adminName)) {

            return new ResponseResult<>(400, "请输入登录名");
        }

        // 没有密码
        if (StringUtils.isBlank(adminPassword)) {

            return new ResponseResult<>(400, "请输入密码");
        }

        // 没有验证码
        if (StringUtils.isBlank(captcha)) {

            return new ResponseResult<>(400, "请输入验证码");
        }

        // 判断验证码是否一致
        if (!RandomValidateCode.verify(request, captcha)) {

            return new ResponseResult<>(400, "验证码错误");
        }

        // 全部数据通过
        UsernamePasswordToken token = new UsernamePasswordToken(adminName, adminPassword);

        try {

            // 登录
            this.loginService.login(token);

            // 将用户信息保存到Session中
            Session session = subject.getSession();

            Admin admin = (Admin) subject.getPrincipal();

            session.setAttribute("admin", admin);

            return new ResponseResult<>(200, "登录成功");

        }
        // 用户名或密码错误
        catch (IncorrectCredentialsException e) {

            e.printStackTrace();

            return new ResponseResult<>(400, "用户名或密码错误");

        }
        // 账户未注册
        catch (UnknownAccountException e) {

            e.printStackTrace();

            return new ResponseResult<>(400, "账号未注册");
        }
        // 账户未激活
        catch (AuthenticationException e) {

            e.printStackTrace();

            return new ResponseResult<>(400, "账号未激活");
        }
    }

    /**
     * 根据用户名查询管理员
     *
     * @param adminName
     * @return
     */
    @Override
    public MessageResult<Admin> selectAdminByName(String adminName) {

        Specification<Admin> specification = new Specification<Admin>() {

            List<Predicate> list = new ArrayList<>();


            @Override
            public Predicate toPredicate(Root<Admin> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                list.add(criteriaBuilder.equal(root.get("adminName").as(String.class), adminName));

                Predicate[] predicates = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(predicates));
            }
        };

        List<Admin> admins = this.adminRepository.findAll(specification);

        if (CollectionUtils.isEmpty(admins)) {

            return new MessageResult<>(400, "无数据", null);
        }

        return new MessageResult<>(200, "查询成功", admins.get(0));
    }

    /**
     * 注册操作
     *
     * @param adminName
     * @param adminPassword
     * @param captcha
     * @param adminEmail
     * @return
     */
    @Override
    public ResponseResult<Void> register(String adminName, String adminPassword, String captcha, String adminEmail, HttpServletRequest request) {

        // 判断参数

        // 没有注册名
        if (StringUtils.isBlank(adminName)) {

            return new ResponseResult<>(400, "请输入注册名");
        }

        // 没有密码
        if (StringUtils.isBlank(adminPassword)) {

            return new ResponseResult<>(400, "请输入密码");
        }

        // 没有邮箱
        if (StringUtils.isBlank(adminEmail)) {

            return new ResponseResult<>(400, "请输入邮箱");
        }

        // 邮箱格式错误
        if (!Pattern.matches(emailRegex, adminEmail)) {

            return new ResponseResult<>(400, "邮箱格式错误");
        }
        // 没有验证码
        if (StringUtils.isBlank(captcha)) {

            return new ResponseResult<>(400, "请输入验证码");

        }

        // 验证码错误
        if (!RandomValidateCode.verify(request, captcha)) {

            return new ResponseResult<>(400, "验证码错误");
        }

        // 根据管理员名称查询管理员是否存在
        MessageResult<Admin> messageResult = this.selectAdminByName(adminName);

        if (messageResult.getData() != null) {

            return new ResponseResult<>(400, "该管理员已存在");
        }

        // 设置基本信息
        Admin admin = new Admin();

        admin.setAdminName(adminName);

        admin.setAdminPassword(adminPassword);

        admin.setAdminEmail(adminEmail);

        admin.setAdminCreateTime(new Date());

        admin.setAdminStatus(0);

        admin.setAdminDelete(1);

        try {

            // 执行插入
            this.adminRepository.save(admin);

            // 设置新注册的管理员为注册用户
            AdminRole adminRole = new AdminRole();

            messageResult = this.selectAdminByName(adminName);

            if (messageResult.getData() == null) {

                return new ResponseResult<>(500, "注册失败");
            }

            adminRole.setAdminId(messageResult.getData().getAdminId());

            adminRole.setRoleId(2L);

            // 执行插入
            this.adminRoleRepository.save(adminRole);

            // 查询所有的菜单

            Specification<Menu> specification = new Specification<Menu>() {

                List<Predicate> list = new ArrayList<>();

                @Override
                public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                    list.add(criteriaBuilder.equal(root.get("isMenu").as(String.class), 0));

                    Predicate[] predicates = new Predicate[list.size()];

                    return criteriaBuilder.and(list.toArray(predicates));
                }
            };

            // 执行查询
            List<Menu> menus = this.menuRepository.findAll(specification);

            if (CollectionUtils.isEmpty(menus)) {

                return new ResponseResult<>(200, "注册成功");
            }

            // 循环插入
            for (Menu menu : menus
            ) {

                // 创建菜单角色对象
                RoleMenu roleMenu = new RoleMenu();

                roleMenu.setRoleId(2L);

                roleMenu.setMenuId(menu.getAuthorityId());

                // 执行插入
                this.roleMenuRepository.save(roleMenu);
            }

            // 将信息转换成Html格式
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("<html>");

            stringBuilder.append("<head>");

            stringBuilder.append("<title>管理员激活</title>");

            stringBuilder.append("</head>");

            stringBuilder.append("<body>");

            stringBuilder.append("<p>你已成功注册，请点击下列连接进行激活</p>");

            stringBuilder.append("<p>激活地址: http://localhost:8000/admin/active/" + messageResult.getData().getAdminId() + "</p>");

            stringBuilder.append("</body>");

            stringBuilder.append("</html>");

            this.mailService.toEmail(messageResult.getData().getAdminEmail(),stringBuilder.toString());

            return new ResponseResult<>(200, "注册成功,请到邮箱激活");

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseResult<>(500, "注册失败");
        }
    }

    /**
     * 激活管理员
     *
     * @param adminId
     * @return
     */
    @Override
    public ResponseResult<Void> active(Long adminId) {

        if (adminId == null) {

            return new ResponseResult<>(400, "参数错误");
        }

        try {
            // 根据Id查询管理员
            Optional<Admin> optionalAdmin = this.adminRepository.findById(adminId);

            Admin admin = optionalAdmin.get();

            if (adminId == null) {

                return new ResponseResult<>(400, "没有该管理员");
            }

            // 修改状态
            admin.setAdminStatus(1);

            // 执行修改
            this.adminRepository.saveAndFlush(admin);

            return new ResponseResult<>(200, "激活成功");

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseResult<>(500, "激活失败");
        }
    }
}
