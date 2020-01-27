package com.feicheng.authority.system.service.impl;

import com.feicheng.authority.common.response.MessageResult;
import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.system.entity.Admin;
import com.feicheng.authority.system.entity.AdminRole;
import com.feicheng.authority.system.entity.Role;
import com.feicheng.authority.system.entity.RoleMenu;
import com.feicheng.authority.system.repository.AdminRoleRepository;
import com.feicheng.authority.system.repository.RoleMenuRepository;
import com.feicheng.authority.system.repository.RoleRepository;
import com.feicheng.authority.system.service.AdminService;
import com.feicheng.authority.system.service.RoleService;
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
import java.util.Optional;

/**
 * 角色Service
 *
 * @author Lenovo
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired(required = false)
    private RoleRepository roleRepository;

    @Autowired(required = false)
    private RoleMenuRepository roleMenuRepository;

    @Autowired(required = false)
    private AdminRoleRepository adminRoleRepository;

    @Autowired(required = false)
    private AdminService adminService;


    /**
     * 分页查询角色
     *
     * @param page
     * @param limit
     * @return
     */
    @Override
    public ResponseResult<Role> list(Integer page, Integer limit, String roleName) {

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.Direction.ASC, "roleId");

        Specification<Role> specification = new Specification<Role>() {

            List<Predicate> list = new ArrayList<>();

            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                // 判断是否通过名称搜索
                if (StringUtils.isNotBlank(roleName)) {

                    list.add(criteriaBuilder.like(root.get("roleName").as(String.class), roleName));

                }

                Predicate[] predicates = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(predicates));
            }
        };

        Page<Role> pageResult = this.roleRepository.findAll(specification, pageable);

        if (CollectionUtils.isEmpty(pageResult.getContent())) {

            // 数据为空
            return new ResponseResult<>(-1, "无数据", null, 0L);
        }

        return new ResponseResult<>(0, "查询成功", pageResult.getContent(), (long) pageResult.getSize());
    }

    /**
     * 修改角色信息及权限
     *
     * @param role
     * @return
     */
    @Override
    public ResponseResult<Void> edit(Role role) {

        // 校验数据
        // 校验角色名
        if (StringUtils.isBlank(role.getRoleName())) {

            return new ResponseResult<>(400, "请输入角色名称");
        }

        // 校验角色描述
        if (StringUtils.isBlank(role.getRemark())) {

            return new ResponseResult<>(400, "请输入角色描述");
        }

        // 设置基本信息
        role.setCreateTime(new Date());

        role.setModifyTime(role.getCreateTime());

        try {

            // 执行修改
            this.roleRepository.saveAndFlush(role);

            // 插入新的权限数据
            // 角色有的权限转换成List集合
            List<Long> menuIds = changeStringToList(role.getRoleMenu());

            // 若修改的角色权限为空
            if (CollectionUtils.isEmpty(menuIds)) {

                // 先查询该角色是否有菜单权限
                List<Long> roleMenuIds = this.roleMenuRepository.select(role.getRoleId());

                // 没有菜单权限
                if (CollectionUtils.isEmpty(roleMenuIds)) {

                    return new ResponseResult<>(200, "修改成功");
                }

                // 有菜单权限，直接删除
                this.roleMenuRepository.deleteRoleMenuByRoleId(role.getRoleId());

                return new ResponseResult<>(200, "修改成功");
            }

            // 若修改的权限不为空
            // 先查询该角色是否有菜单权限
            List<Long> roleMenuIds = this.roleMenuRepository.select(role.getRoleId());

            // 没有菜单权限
            if (CollectionUtils.isEmpty(roleMenuIds)) {

                // 直接删除菜单权限
                // 不执行删除，直接添加新的菜单权限
                for (Long menuId : menuIds
                ) {

                    // 创建对象
                    RoleMenu roleMenu = new RoleMenu();

                    roleMenu.setRoleId(role.getRoleId());

                    roleMenu.setMenuId(menuId);

                    // 插入数据
                    this.roleMenuRepository.save(roleMenu);
                }
                return new ResponseResult<>(200, "修改成功");
            }

            // 有菜单权限，直接删除
            this.roleMenuRepository.deleteRoleMenuByRoleId(role.getRoleId());

            // 直接删除菜单权限
            // 不执行删除，直接添加新的菜单权限
            for (Long menuId : menuIds
            ) {

                // 创建对象
                RoleMenu roleMenu = new RoleMenu();

                roleMenu.setRoleId(role.getRoleId());

                roleMenu.setMenuId(menuId);

                // 插入数据
                this.roleMenuRepository.save(roleMenu);
            }

            return new ResponseResult<>(200, "修改成功");

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseResult<>(500, "修改失败");
        }

    }

    /**
     * 删除角色信息及权限
     *
     * @param roleId
     * @return
     */
    @Override
    public ResponseResult<Void> delete(Long roleId) {

        try {

            // 先删除角色下的菜单权限
            this.roleMenuRepository.deleteRoleMenuByRoleId(roleId);

            // 删除管理员的角色
            this.adminRoleRepository.deleteByRoleId(roleId);

            // 删除该角色信息
            this.roleRepository.deleteById(roleId);

            return new ResponseResult<>(200, "删除成功");

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseResult<>(500, "删除失败");
        }

    }

    /**
     * 添加角色及权限
     *
     * @param role
     * @return
     */
    @Override
    public ResponseResult<Void> add(Role role) {

        // 判断参数
        if (StringUtils.isBlank(role.getRoleName())) {

            return new ResponseResult<>(400, "请输入角色名称");
        }

        if (StringUtils.isBlank(role.getRemark())) {

            return new ResponseResult<>(400, "请输入角色描述");
        }

        // 设置基本信息
        role.setCreateTime(new Date());

        role.setModifyTime(role.getCreateTime());

        try {

            // 将角色设置菜单权限为List对象
            List<Long> roleMenuIds = this.changeStringToList(role.getRoleMenu());

            // 没有权限
            if (CollectionUtils.isEmpty(roleMenuIds)) {

                // 直接添加角色信息
                this.roleRepository.save(role);

                return new ResponseResult<>(200, "添加成功");
            }

            // 有菜单权限

            // 先添加角色信息
            this.roleRepository.saveAndFlush(role);

            for (Long menuId : roleMenuIds
            ) {

                // 创建对象
                RoleMenu roleMenu = new RoleMenu();

                roleMenu.setRoleId(role.getRoleId());

                roleMenu.setMenuId(menuId);

                // 插入数据
                this.roleMenuRepository.save(roleMenu);
            }

            return new ResponseResult<>(200, "添加成功");

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseResult<>(500, "添加失败");
        }
    }

    /**
     * 根据管理员获取角色集
     *
     * @param adminName
     * @return
     */
    @Override
    public List<Role> selectAdminRole(String adminName) {

        List<Role> roles = new ArrayList<>();

        MessageResult<Admin> messageResult = this.adminService.selectAdminByName(adminName);

        if (messageResult.getData() == null) {

            return roles;
        }

        roles = listAdminRoles(messageResult.getData());

        if (CollectionUtils.isEmpty(roles)){

            return roles;
        }

        return roles;
    }


    /**
     * 查询管理员角色
     *
     * @param admin
     * @return
     */
    private List<Role> listAdminRoles(Admin admin) {

        List<Role> roles = new ArrayList<>();

        Specification<AdminRole> specification = new Specification<AdminRole>() {

            List<Predicate> list = new ArrayList<>();

            @Override
            public Predicate toPredicate(Root<AdminRole> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                list.add(criteriaBuilder.equal(root.get("adminId").as(Long.class), admin.getAdminId()));

                Predicate[] predicates = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(predicates));
            }
        };

        List<AdminRole> adminRoles = this.adminRoleRepository.findAll(specification);

        if (CollectionUtils.isEmpty(adminRoles)) {

            return roles;
        }

        // 循环查询角色信息
        for (AdminRole adminRole : adminRoles
        ) {

            Optional<Role> optionalRole = this.roleRepository.findById(adminRole.getRoleId());

            // 添加到List集合中
            roles.add(optionalRole.get());
        }

        // 返回数据
        return roles;
    }


    /**
     * 将用逗号隔开的字符串转换成List对象
     *
     * @param roleMenu
     * @return
     */
    private List<Long> changeStringToList(String roleMenu) {

        // 若没有添加权限信息
        if (StringUtils.isBlank(roleMenu)) {

            return null;
        }

        // 添加了权限信息

        // 将字符串分割成数组
        String[] split = roleMenu.split(",");

        // 循环遍历将String装换成Long

        List<Long> roleMenuIds = new ArrayList<Long>();

        for (String menuId : split
        ) {

            roleMenuIds.add(Long.parseLong(menuId));
        }

        // 移除数据为1的数据
        roleMenuIds.remove(0);

        return roleMenuIds;
    }


}
