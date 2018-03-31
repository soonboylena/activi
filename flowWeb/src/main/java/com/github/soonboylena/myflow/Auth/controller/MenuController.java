package com.github.soonboylena.myflow.Auth.controller;

import com.github.soonboylena.myflow.Auth.bean.Menu;
import com.github.soonboylena.myflow.Auth.bean.Message;
import com.github.soonboylena.myflow.Auth.service.MenuService;
import com.github.soonboylena.myflow.persistentneo4j.entity.MenuNode;
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
        boolean exists = menuService.existsByCurrentKey(currentKey);
        if (exists) {
            return new Message<>("此id已被占用", false);
        } else {
            return new Message<>("此id可用", true);
        }
    }

    @PostMapping("/add/{pId}")
    public Message<Boolean> addMenu(@RequestBody Menu menu, @PathVariable("pId") Long pId) {
        Assert.hasText(menu.getCurrentKey(), "菜单的key值不能为空");
        Menu saved = menuService.addMenu(pId, menu);
        if (saved != null) {
            return new Message<>("添加成功", true);
        } else {
            return new Message<>("添加失败", false);
        }
    }

    @PostMapping("/update")
    public Message<Boolean> editMenu(@RequestBody Menu menu) {
        Assert.hasText(menu.getCurrentKey(), "菜单的key值不能为空");
        menuService.updateMenu(menu);
        return new Message<>("添加成功", true);
    }

    @GetMapping("/menus")
    public List<MenuNode> getMenuTree() {
        return menuService.generateAllMenuTree();
    }

    @DeleteMapping("/{id:\\d+}")
    public Message<Boolean> deleteMenu(@PathVariable long id) {
        menuService.deleteMenu(id);
        return new Message<>("删除成功", true);
    }
}
