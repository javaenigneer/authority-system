package com.feicheng.authority.system.controller.menu;

import com.feicheng.authority.common.response.MenuTree;
import com.feicheng.authority.common.response.ResponseResult;
import com.feicheng.authority.system.entity.Menu;
import com.feicheng.authority.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 菜单控制器
 *
 * @author Lenovo
 */
@Controller
@RequestMapping("menu")
public class MenuController {


    @Autowired(required = false)
    private MenuService menuService;


    /**
     * 获取菜单
     *
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<ResponseResult<Menu>> list() {

        ResponseResult<Menu> responseResult = this.menuService.list();

        return ResponseEntity.ok(responseResult);
    }

    /**
     * 修改菜单
     *
     * @param menu
     * @return
     */
    @PostMapping("edit")
    public ResponseEntity<ResponseResult<Void>> edit(Menu menu) {

        ResponseResult<Void> responseResult = this.menuService.edit(menu);

        return ResponseEntity.ok(responseResult);
    }


    /**
     * 添加菜单
     *
     * @param menu
     * @return
     */
    @PostMapping("add")
    public ResponseEntity<ResponseResult<Void>> add(Menu menu) {

        ResponseResult<Void> responseResult = this.menuService.add(menu);

        return ResponseEntity.ok(responseResult);
    }


    /**
     * 删除菜单
     *
     * @param authorityId
     * @param isMenu
     * @return
     */
    @PostMapping("delete")
    public ResponseEntity<ResponseResult<Void>> delete(@RequestParam("authorityId") Long authorityId,
                                                       @RequestParam("isMenu") Integer isMenu) {

        ResponseResult<Void> responseResult = this.menuService.delete(authorityId, isMenu);

        return ResponseEntity.ok(responseResult);
    }


    /**
     * 获取节点树的数据
     * @return
     */
    @GetMapping("tree")
    public ResponseEntity<List<MenuTree<Menu>>> selectNodes(){

        MenuTree<Menu> menuTrees = this.menuService.selectNodes();

        return ResponseEntity.ok(menuTrees.getChildren());
    }
}
