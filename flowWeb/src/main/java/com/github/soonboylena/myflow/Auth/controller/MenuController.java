package com.github.soonboylena.myflow.Auth.controller;

import com.github.soonboylena.myflow.Auth.bean.Menu;
import com.github.soonboylena.myflow.Auth.bean.MenuNode;
import com.github.soonboylena.myflow.Auth.bean.Message;
import com.github.soonboylena.myflow.Auth.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author lungern xiii.at.cn@gmail.com
 * @date 2018/2/5
 */

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/usable/{currentKey}")
    public Message<Boolean> checkIfUse(@PathVariable String currentKey) {
        List<Menu> menuList = menuService.findMenuByCurrentKey(currentKey);
        if (null != menuList && menuList.size() > 0) {
            return new Message<>("此id已被占用", false);
        } else {
            return new Message<>("此id可用", true);
        }
    }

    @PostMapping("/add")
    public Message<Boolean> addMenu(@RequestBody Menu menu) {
        Assert.hasText(menu.getCurrentKey(), "菜单的key值不能为空");
        boolean flag = menuService.addMenu(menu);
        if (flag) {
            return new Message<>("添加成功", flag);
        } else {
            return new Message<>("添加失败", flag);
        }
    }

    @PostMapping("/update")
    public Message<Boolean> editMenu(@RequestBody Menu menu) {
        Assert.hasText(menu.getCurrentKey(), "菜单的key值不能为空");
        boolean flag = menuService.addMenu(menu);
        if (flag) {
            return new Message<>("更新成功", flag);
        } else {
            return new Message<>("更新失败", flag);
        }
    }

    @GetMapping("/menus")
    public List<MenuNode> getMenuTree() {
        return menuService.generateAllMenuTree();
    }

    @DeleteMapping("/{id:\\d+}")
    public Message<Boolean> deleteMenu(@PathVariable long id) {
        boolean flag = menuService.deleteMenu(id);
        if (flag) {
            return new Message<>("删除成功", flag);
        } else {
            return new Message<>("删除失败", flag);
        }
    }
}
