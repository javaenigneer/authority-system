package com.feicheng.authority.system.service.impl;

import com.feicheng.authority.common.response.MenuTree;
import com.feicheng.authority.common.response.MessageResult;
import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.system.entity.Admin;
import com.feicheng.authority.system.entity.AdminRole;
import com.feicheng.authority.system.entity.Menu;
import com.feicheng.authority.system.entity.RoleMenu;
import com.feicheng.authority.system.repository.AdminRoleRepository;
import com.feicheng.authority.system.repository.MenuRepository;
import com.feicheng.authority.system.repository.RoleMenuRepository;
import com.feicheng.authority.system.service.AdminService;
import com.feicheng.authority.system.service.MenuService;
import com.feicheng.authority.utils.StringUtil;
import com.feicheng.authority.utils.TreeUtil;
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
 * 菜单Service
 *
 * @author Lenovo
 */
@Service
public class MenuServiceImpl implements MenuService {


    @Autowired(required = false)
    private MenuRepository menuRepository;

    @Autowired(required = false)
    private RoleMenuRepository roleMenuRepository;

    @Autowired(required = false)
    private AdminService adminService;

    @Autowired(required = false)
    private AdminRoleRepository adminRoleRepository;

    /**
     * 查询菜单
     *
     * @return
     */
    @Override
    public ResponseResult<Menu> list() {

        List<Menu> menus = this.menuRepository.findAll();

        if (CollectionUtils.isEmpty(menus)) {

            return new ResponseResult<>(-1, "无数据", null, 0L);
        }

        return new ResponseResult<>(0, "查询成功", menus, (long) menus.size());
    }

    /**
     * 修改菜单
     *
     * @param menu
     * @return
     */
    @Override
    public ResponseResult<Void> edit(Menu menu) {

        // 判断是否参数
        if (menu.getAuthorityId() == null) {

            return new ResponseResult<>(400, "参数错误");
        }

        // 没有菜单名称
        if (StringUtils.isBlank(menu.getAuthorityName())) {

            return new ResponseResult<>(400, "请输入菜单名称");
        }

        // 没有菜单Url
        if (StringUtils.isBlank(menu.getMenuUrl())) {

            return new ResponseResult<>(400, "请输入菜单Url");
        }

        // 根据Id获取对象数据
        Optional<Menu> optionalMenu = this.menuRepository.findById(menu.getAuthorityId());

        Menu menuData = optionalMenu.get();

        if (menuData == null) {

            return new ResponseResult<>(400, "数据错误");
        }

        // 修改数据
        menuData.setAuthorityName(menu.getAuthorityName());

        menuData.setAuthority(menu.getAuthority());

        menuData.setMenuUrl(menu.getMenuUrl());

        try {

            // 更新数据
            this.menuRepository.saveAndFlush(menuData);

            return new ResponseResult<>(200, "修改成功");

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseResult<>(500, "修改失败");
        }

    }

    /**
     * 添加菜单
     *
     * @param menu
     * @return
     */
    @Override
    public ResponseResult<Void> add(Menu menu) {

        // 判断是否添加新的菜单
        if (menu.getAuthorityId() != -2) {

            return new ResponseResult<>(400, "参数错误");
        }

        // 设置为null
        menu.setAuthorityId(null);

        // 设置基本信息
        menu.setCreateTime(new Date());

        menu.setUpdateTime(menu.getCreateTime());

        try {
            // 执行添加操作
            this.menuRepository.save(menu);

            // 更新数据
            menu.setOrderNumber(menu.getOrderNumber());

            this.menuRepository.saveAndFlush(menu);

            return new ResponseResult<>(200, "添加成功");

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseResult<>(500, "添加失败");
        }
    }

    /**
     * 删除菜单
     *
     * @param authorityId
     * @param isMenu
     * @return
     */
    @Override
    public ResponseResult<Void> delete(Long authorityId, Integer isMenu) {

        // 判断参数是否为空
        if (authorityId == null || isMenu == null) {

            return new ResponseResult<>(400, "参数错误");
        }

        // 判断是否是菜单
        if (isMenu == 0) {

            // 先删除子菜单
            // 查询是否有子菜单
            Pageable pageable = PageRequest.of(0, 100, Sort.Direction.ASC, "authorityId");

            Specification<Menu> specification = new Specification<Menu>() {

                @Override
                public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                    List<Predicate> list = new ArrayList<>();

                    list.add(criteriaBuilder.equal(root.get("parentId").as(Integer.class), authorityId));

                    Predicate[] predicates = new Predicate[list.size()];

                    return criteriaBuilder.and(list.toArray(predicates));

                }
            };

            Page<Menu> pageResult = this.menuRepository.findAll(specification, pageable);

            // 若没有子菜单
            if (CollectionUtils.isEmpty(pageResult.getContent())) {

                try {

                    // 直接删除父菜单
                    this.menuRepository.deleteById(authorityId);

                    // 删除角色中的菜单权限
                    this.roleMenuRepository.delete(authorityId);

                    return new ResponseResult<>(200, "删除成功");

                } catch (Exception e) {

                    e.printStackTrace();

                    return new ResponseResult<>(500, "删除失败");
                }
            }

            try {

                // 若有子菜单
                // 先删除子菜单
                this.menuRepository.deleteMenuByParentId(authorityId);

                // 删除主菜单
                this.menuRepository.deleteById(authorityId);

                // 循环删除角色中的菜单权限
                for (Menu menu : pageResult.getContent()
                ) {

                    this.roleMenuRepository.delete(menu.getAuthorityId());
                }

                // 删除最顶级菜单权限
                this.roleMenuRepository.delete(authorityId);

                return new ResponseResult<>(200, "删除成功");

            } catch (Exception e) {

                e.printStackTrace();

                return new ResponseResult<>(500, "删除失败");
            }
        }

        // 若是按钮
        if (isMenu == 1) {

            try {
                // 直接删除按钮菜单
                this.menuRepository.deleteById(authorityId);

                // 删除角色中的菜单权限
                this.roleMenuRepository.delete(authorityId);

                return new ResponseResult<>(200, "删除成功");

            } catch (Exception e) {

                e.printStackTrace();

                return new ResponseResult<>(500, "删除失败");
            }
        }

        return new ResponseResult<>(500, "操作失败");
    }

    /**
     * 获取所有节点树的所有数据
     *
     * @return
     */
    @Override
    public MenuTree<Menu> selectNodes() {

        // 获取所有的数据
        List<Menu> menus = this.menuRepository.findAll();

        // 转换成节点树
        List<MenuTree<Menu>> menuTrees = this.covertMenus(menus);

        // 返回构建好的节点树
        return TreeUtil.buildMenuTree(menuTrees);
    }

    /**
     * 根据管理员获取管理员的菜单权限
     *
     * @param adminName
     * @return
     */
    @Override
    public List<Menu> selectAdminPermission(String adminName) {

        List<Menu> menus = new ArrayList<>();

        // 若adminName为空
        if (StringUtils.isBlank(adminName)) {

            return menus = null;
        }

        // 根据管理员名称获取管理员
        MessageResult<Admin> messageResult = this.adminService.selectAdminByName(adminName);

        if (messageResult.getData() == null) {

            return menus = null;
        }

        // 根据管理员Id查询管理员角色
        Specification<AdminRole> specification = new Specification<AdminRole>() {

            List<Predicate> list = new ArrayList<>();

            @Override
            public Predicate toPredicate(Root<AdminRole> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                list.add(criteriaBuilder.equal(root.get("adminId").as(Long.class), messageResult.getData().getAdminId()));

                Predicate[] predicates = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(predicates));
            }
        };

        // 执行查询
        List<AdminRole> adminRoles = this.adminRoleRepository.findAll(specification);

        // 若没有角色
        if (CollectionUtils.isEmpty(adminRoles)) {

            return menus = null;
        }

        // 根据角色Id查询菜单权限
        for (AdminRole adminRole : adminRoles) {

            Specification<RoleMenu> roleMenuSpecification = new Specification<RoleMenu>() {

                List<Predicate> list = new ArrayList<>();

                @Override
                public Predicate toPredicate(Root<RoleMenu> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                    list.add(criteriaBuilder.equal(root.get("roleId").as(Long.class), adminRole.getRoleId()));


                    Predicate[] predicates = new Predicate[list.size()];

                    return criteriaBuilder.and(list.toArray(predicates));
                }
            };

            // 执行查询
            List<RoleMenu> roleMenus = this.roleMenuRepository.findAll(roleMenuSpecification);

            // 循环遍历该角色下的所有菜单权限
            for (RoleMenu roleMenu : roleMenus
            ) {

                Optional<Menu> optionalMenu = this.menuRepository.findById(roleMenu.getMenuId());

                // 添加到menus中
                menus.add(optionalMenu.get());
            }
        }
        return menus;
    }


    /**
     * 转化成节点树
     *
     * @param menus
     * @return
     */
    private List<MenuTree<Menu>> covertMenus(List<Menu> menus) {

        List<MenuTree<Menu>> trees = new ArrayList<>();

        menus.forEach(menu -> {

            MenuTree<Menu> tree = new MenuTree<>();

            tree.setId(String.valueOf(menu.getAuthorityId()));

            tree.setParentId(String.valueOf(menu.getParentId()));

            tree.setTitle(menu.getAuthorityName());

            tree.setHref(menu.getMenuUrl());

            tree.setData(menu);

            trees.add(tree);
        });

        return trees;
    }
}
