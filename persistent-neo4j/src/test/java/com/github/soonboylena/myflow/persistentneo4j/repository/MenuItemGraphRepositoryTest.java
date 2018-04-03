package com.github.soonboylena.myflow.persistentneo4j.repository;

import com.github.soonboylena.myflow.persistentneo4j.NeoBaseTest;
import com.github.soonboylena.myflow.persistentneo4j.entity.MenuItem;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MenuItemGraphRepositoryTest extends NeoBaseTest {

    @Autowired
    MenuItemGraphRepository repository;

    @Test
    public void findMenuByParentKeyAndExpress() {
        Set<String> expresses = new HashSet<>();
        expresses.add("menu-system-auth");
        expresses.add("menu-system-user");

        List<MenuItem> menuTreesByExpress = repository.findMenuByParentKeyAndExpress("systemSetting", expresses);
//        List<MenuNode> menuTreesByExpress = repository.findMenuTreesByExpress("p11");
        print(menuTreesByExpress, "测试根据权限取菜单");
    }
}