package com.github.soonboylena.myflow.Auth.service;

import com.github.soonboylena.myflow.Auth.bean.Menu;
import com.github.soonboylena.myflow.persistentneo4j.entity.AuthorityEntity;
import com.github.soonboylena.myflow.persistentneo4j.entity.MenuNode;
import com.github.soonboylena.myflow.persistentneo4j.repository.MenuNodeGraphRepository;
import org.apache.commons.collections4.set.UnmodifiableSet;
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

    public List<MenuNode> allMenu() {
        Iterable<MenuNode> menuList = menuRepository.findAll();
//        sortList(menuList);
//        return menuList;
        return null;
    }

    public List<MenuNode> generateAllMenuTree() {
        List<MenuNode> menuList = allMenu();
        return generateMenuTree(menuList);
    }

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
                    parentNode.addSubNode(menuMap.get(element.getCurrentKey()));
                }
            }
        });

        List<MenuNode> bak = new ArrayList<>(menuTree.values());
        //TODO
//        bak.forEach(MenuNode::sortChildrenOrder);
//        MenuNode menuNode = new MenuNode(new Menu());
//        menuNode.setChildren(bak);
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

    public Menu findMenuById(Long id) {
//        return menuRepository.findOne(id);
        return null;
    }


    public Menu addMenu(Long pId, Menu menu) {

        MenuNode menuNode = toDb(menu);
        if (pId == null) {
            logger.info("增加菜单根结点，{}", menu);
            MenuNode save = menuRepository.save(menuNode);
            return fromDb(save);
        }

        MenuNode one = menuRepository.findOne(pId);
        if (one == null) {
            throw new RuntimeException("没有找到父结点：" + pId);
        }
        one.addSubNode(menuNode);
        MenuNode pNode = menuRepository.save(one);
        return fromDb(menuNode);
    }

    private MenuNode toDb(Menu menu) {
        MenuNode node = new MenuNode();
        node.setUrl(menu.getUrl());
        node.setTitle(menu.getTitle());
        node.setPicUrl(menu.getPicUrl());
        node.setIcon(menu.getIcon());
        node.setDescription(menu.getDescription());
        node.setCurrentKey(menu.getCurrentKey());
        return node;
    }

    public List<Menu> getUserMenu(UserDetails user) {
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        if (authorities == null || authorities.isEmpty()) {
            return Collections.emptyList();
        }

        Set<String> collect = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

//        Set<AuthorityEntity> authSet = new HashSet<>();
//
//        if (User.class.isInstance(user)) {
//            //
//            User _user = (User) user;
//            Collection<GrantedAuthority> authorities1 = _user.getAuthorities();
//
//        }
//
//        for (GrantedAuthority authority : authorities) {
//            if (authority instanceof AuthorityEntity) {
//                authSet.add((AuthorityEntity) authority);
//            } else {
//                throw new RuntimeException("UserDetails的实例应该都是AuthorityEntity.现在类型是： " + authorities.getClass().getName());
//            }
//        }

        List<MenuNode> menus = menuRepository.findMenuTreesByExpress(collect);
        if (menus == null || menus.isEmpty()) {
            return Collections.emptyList();
        }
        return menus.stream().map(this::fromDb).collect(Collectors.toList());
    }

    private Menu fromDb(MenuNode menuNode) {
        Menu menu = new Menu();
        menu.setCurrentKey(menuNode.getCurrentKey());
        menu.setDescription(menuNode.getDescription());
        menu.setIcon(menuNode.getIcon());
        menu.setPicUrl(menuNode.getPicUrl());
        menu.setTitle(menuNode.getTitle());
        menu.setUrl(menuNode.getUrl());
        return menu;
    }

    public List<Menu> getSubMenus(String parentKey) {
//        List<Menu> allMenus = allMenu();
//        List<MenuItem> requiredMenus = new ArrayList<>();
//        allMenus.forEach(menu -> {
//            if (parentKey.equals(menu.getParentKey())) {
//                requiredMenus.add(convertMenu2MenuItem(menu));
//            }
//        });
//        return requiredMenus;
        return null;
    }


    public void deleteMenu(long id) {
        menuRepository.delete(id);
    }

    public void updateMenu(Menu menu) {
        MenuNode menuNode = toDb(menu);
        menuRepository.save(menuNode);
    }
}
