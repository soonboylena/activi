package com.github.soonboylena.myflow.Auth.service;

import com.github.soonboylena.myflow.Auth.bean.Menu;
import com.github.soonboylena.myflow.persistentneo4j.entity.MenuItem;
import com.github.soonboylena.myflow.persistentneo4j.entity.MenuNode;
import com.github.soonboylena.myflow.persistentneo4j.repository.MenuItemGraphRepository;
import com.github.soonboylena.myflow.persistentneo4j.repository.MenuNodeGraphRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lungern xiii.at.cn@gmail.com
 * @date 2018/2/2
 */

@Service
public class MenuService {


    private static final Logger logger = LoggerFactory.getLogger(MenuService.class);

    @Autowired
    private MenuNodeGraphRepository menuRepository;

    @Autowired
    private MenuItemGraphRepository menuItemGR;

//    private static void sortList(List<Menu> menuList) {
//        menuList.sort((item1, item2) -> {
//            if (item1.getMenuOrder() > item2.getMenuOrder()) {
//                return 1;
//            } else if (item1.getMenuOrder() == item2.getMenuOrder()) {
//                return 0;
//            }
//            return -1;
//        });
//    }

    public List<Menu> allMenu() {
        List<MenuNode> menuList = menuRepository.findAllMenuNodeAndItem();
        List<Menu> collect = menuList.stream().map(this::fromDb).collect(Collectors.toList());
        return collect;
    }


//    public List<MenuConstruct> generateAllMenuTree() {
//        List<MenuConstruct> menuList = allMenu();
//        return generateMenuTree(menuList);
//        return menuList;
//    }

    private List<MenuNode> generateMenuTree(List<MenuNode> menuList) {

        Map<String, MenuNode> menuMap = new HashMap<>();

        TreeMap<String, MenuNode> menuTree = new TreeMap<>();

        menuList.forEach(element -> {
//            MenuNode menuNode = new MenuNode(element);
//            menuMap.put(element.getCurrentKey(), menuNode);
//            if (null == element.getParentKey() || "".equals(element.getParentKey())) {
//                menuTree.put(element.getCurrentKey(), menuNode);
//            }
        });

        menuList.forEach(element -> {
            if (null != element.getParentKey()) {
                MenuNode parentNode = menuMap.get(element.getParentKey());
                if (null != parentNode) {
                    // TODO
//                    parentNode.addItem(menuMap.get(element.getCurrentKey()));
                }
            }
        });

        List<MenuNode> bak = new ArrayList<>(menuTree.values());
        //TODO
//        bak.forEach(MenuNode::sortChildrenOrder);
//        MenuNode menuNode = new MenuNode(new Menu());
//        menuNode.setSubNode(bak);
//        menuNode.sortChildrenOrder();
        return bak;
    }

    public boolean existsByCurrentKey(String currentKey) {
        boolean exists = menuRepository.existsByCurrentKey(currentKey);
        return exists;
    }

    public List<Menu> findMenuByCurrentKeys(List<String> keyList) {
//        List<MenuNode> menuList = menuRepository.findAllByCurrentKeyIn(keyList);
//        return menuList;
        return null;
    }


    public Menu addMenu(Long pId, Menu menu) {

        MenuNode menuNode = toDb(menu);
        if (pId == null) {
            logger.info("增加菜单根结点，{}", menu);
            MenuNode save = menuRepository.save(menuNode);
            return fromDb(save);
        }

        MenuNode one = menuRepository.findById(pId).orElseThrow(() -> new RuntimeException("没有找到父结点"));
        //TODO
//        one.addSubNode(menuNode);
        MenuNode pNode = menuRepository.save(one);
        return fromDb(menuNode);
    }

    private MenuNode toDb(Menu menu) {
        MenuNode node = new MenuNode();
        node.setUrl(menu.getUrl());
        node.setTitle(menu.getTitle());
        node.setIcon(menu.getIcon());
        node.setCurrentKey(menu.getCode());
        return node;
    }

    public List<Menu> getUserMenu(UserDetails user) {
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        if (authorities == null || authorities.isEmpty()) {
            return Collections.emptyList();
        }

        Set<String> collect = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        List<MenuNode> menus = menuRepository.findMenuByExpress(collect);
        if (menus == null || menus.isEmpty()) {
            return Collections.emptyList();
        }
        return menus.stream().map(this::fromDb).collect(Collectors.toList());
    }


    private Menu fromDb(MenuNode menuNode) {
        Menu menu = new Menu();
        menu.setCode(menuNode.getCurrentKey());
        menu.setUrl(menuNode.getUrl());
        menu.setTitle(menuNode.getTitle());
        menu.setIcon(menuNode.getIcon());

        List<MenuItem> items = menuNode.getItems();
        if (items == null) {
            return menu;
        }

        for (MenuItem item : items) {
            menu.addSubMenu(fromDb(item));
        }

        return menu;
    }

    private static Menu getMenu(String currentKey, String icon, String title, String url) {
        Menu menu = new Menu();
        menu.setCode(currentKey);
        menu.setIcon(icon);
        menu.setTitle(title);
        menu.setUrl(url);
        return menu;
    }

    private Menu fromDb(MenuItem menuItem) {
        return getMenu(menuItem.getCurrentKey(), menuItem.getIcon(), menuItem.getTitle(), menuItem.getUrl());
    }

    public List<Menu> getSubMenus(String parentKey, User user) {

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        if (authorities == null || authorities.isEmpty()) {
            return Collections.emptyList();
        }

        Set<String> collect = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        List<MenuItem> menuByParentKeyAndExpress = menuItemGR.findMenuByParentKeyAndExpress(parentKey, collect);
        return menuByParentKeyAndExpress.stream().map(this::fromDb).collect(Collectors.toList());
    }


    public void deleteMenu(long id) {
        menuRepository.deleteById(id);
    }


    public void updateMenu(Menu menu) {
        MenuNode menuNode = toDb(menu);
        menuRepository.save(menuNode);
    }
}
