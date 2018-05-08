package com.github.soonboylena.myflow.persistentneo4j.repository;

import com.github.soonboylena.myflow.persistentneo4j.NeoBaseTest;
import com.github.soonboylena.myflow.persistentneo4j.entity.AuthorityEntity;
import com.github.soonboylena.myflow.persistentneo4j.entity.MenuNode;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MenuNodeGraphRepositoryTest extends NeoBaseTest {

    @Autowired
    MenuNodeGraphRepository repository;

    private MenuNode testObject;

    //    @Before
    public void save() {

//        MenuNode menuNode11 = new MenuNode();
//        menuNode11.setCurrentKey("11");
//        menuNode11.setDescription("level 11");
//        menuNode11.setTitle("level 11 label");
//
//        AuthorityEntity auth11 = new AuthorityEntity("p11");
//        auth11.setTitle("权限");
//        auth11.setDescription("菜单权限测试");
//        menuNode11.setAuthorityEntity(auth11);
//
//        MenuNode menuNode111 = new MenuNode();
//        menuNode111.setTitle("level 111 label1");
//        menuNode111.setCurrentKey("111");
//        menuNode111.setDescription("level 111");
//
//        AuthorityEntity entity111 = new AuthorityEntity();
//        entity111.setTitle("111有权限");
//        entity111.setExpress("p111");
//        menuNode111.setAuthorityEntity(entity111);
//        menuNode11.addSubNode(menuNode111);
//
//        MenuNode menuNode112 = new MenuNode();
//        menuNode112.setTitle("level 112 label1");
//        menuNode112.setCurrentKey("112");
//        menuNode112.setDescription("level 112");
//        menuNode11.addSubNode(menuNode112);
//
//        MenuNode menuNode12 = new MenuNode();
//        menuNode12.setTitle("level 12 label");
//        menuNode12.setCurrentKey("12");
//        menuNode12.setDescription("level 12");

//        AuthorityEntity auth = new AuthorityEntity("p12");
//        auth.setTitle("权限");
//        auth.setDescription("菜单权限测试");
//        menuNode12.setAuthorityEntity(auth);
//
//        MenuNode menuNode1 = new MenuNode();
//        menuNode1.setTitle("level 1");
//        menuNode1.setCurrentKey("1");
//
//        menuNode1.addSubNode(menuNode11);
//        menuNode1.addSubNode(menuNode12);

        MenuNode node = new MenuNode();
        node.setTitle("系统设置");
//        node.setCurrentKey("systemSetting");

        MenuNode userItem = new MenuNode("用户管理");
        userItem.setIcon("person-stalker");
        userItem.setUrl("?at=/layoutContent/systemSetting/user");
        userItem.setAuthorityEntity(new AuthorityEntity("用户管理权限", "menu-system-user"));
        node.addItem(userItem);

        MenuNode authItem = new MenuNode("权限管理");
        authItem.setIcon("ios-toggle-outline");
        authItem.setUrl("?at=/layoutContent/systemSetting/auth");
        authItem.setAuthorityEntity(new AuthorityEntity("权限管理菜单权限", "menu-system-auth"));
        node.addItem(authItem);


        MenuNode save = repository.save(node);
        testObject = save;

        MenuNode customer = new MenuNode();
        customer.setTitle("客户管理");

        MenuNode customerAdd = new MenuNode("添加客户");
        customerAdd.setIcon("person-stalker");
        customerAdd.setUrl("/api/page/init/company2?at=/layoutContent/customs/page");
        customerAdd.setAuthorityEntity(new AuthorityEntity("添加客户权限", "menu-customer-add"));
        customer.addItem(customerAdd);
        repository.save(customer);


        MenuNode customerList = new MenuNode("客户一览");
        customerList.setIcon("person-stalker");
        customerList.setUrl("/api/list/layout/company2?at=/layoutContent/customs/page");
        customerList.setAuthorityEntity(new AuthorityEntity("显示客户一览权限", "menu-customer-list"));
        customer.addItem(customerList);
        repository.save(customer);

        print(save, "新提交的一条数据");
    }

    @Test
    public void query() {
        save();
        MenuNode one = repository.findById(testObject.getId()).orElseThrow(RuntimeException::new);
        Assert.assertEquals("确认id相同", one.getId(), testObject.getId());
        print(one, "找到的数据");
//        repository.delete(one);
    }

    @Test
    public void remove() {
        Assert.assertNotNull(testObject);
        repository.delete(testObject);
    }

    @Test
    public void findMenuTreesByExpress() {
        Set<String> expresses = new HashSet<>();
        expresses.add("menu-system-auth");
        expresses.add("menu-system-user");

//        List<MenuNode> menuTreesByExpress = repository.findMenuByExpress(expresses, "systemSetting");
    }


    @Test
    public void findAllMenuNodeAndItem() {
        List<MenuNode> allMenuNodeAndItem = repository.findAllMenuNodeAndItem();
        print(allMenuNodeAndItem, "测试取得所有菜单");
//        Iterable<MenuNode> all = repository.findAll();
//        print(all, "测试取得所有菜单");
    }
}