package com.github.soonboylena.myflow.Auth.service;

import com.github.soonboylena.myflow.Auth.bean.Menu;
import com.github.soonboylena.myflow.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value = "test")
public class MenuServiceTest {

    @Autowired
    MenuService menuService;

    @Test
    public void allMenu() {
        List<Menu> menus = menuService.allMenu();
        BaseTest.print(menus, "所有菜单");
    }

    @Test
    public void getUserMenu() {
    }

    @Test
    public void getSubMenus() {
    }

    @Test
    public void deleteMenu() {
    }

    @Test
    public void updateMenu() {
    }
}