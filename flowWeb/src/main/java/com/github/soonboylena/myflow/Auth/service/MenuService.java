package com.github.soonboylena.myflow.Auth.service;

import com.github.soonboylena.myflow.Auth.bean.Menu;
import com.github.soonboylena.myflow.Auth.bean.MenuNode;
import com.github.soonboylena.myflow.Auth.jpa.MenuRepository;
import com.github.soonboylena.myflow.vModel.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author lungern xiii.at.cn@gmail.com
 * @date 2018/2/2
 */

@Service
public class MenuService {


    private static final Logger logger = LoggerFactory.getLogger(MenuService.class);

    @Autowired
    private MenuRepository menuRepository;

    private static void sortList(List<Menu> menuList) {
        Collections.sort(menuList, (item1, item2) -> {
            if (item1.getMenuOrder() > item2.getMenuOrder()) {
                return 1;
            } else if (item1.getMenuOrder() == item2.getMenuOrder()) {
                return 0;
            }
            return -1;
        });
    }

    public List<Menu> allMenu() {
        List<Menu> menuList = menuRepository.findAll();
        sortList(menuList);
        return menuList;
    }

    public List<MenuNode> generateAllMenuTree() {
        List<Menu> menuList = allMenu();
        return generateMenuTree(menuList);
    }

    private List<MenuNode> generateMenuTree(List<Menu> menuList) {

        Map<String, MenuNode> menuMap = new HashMap<>();

        TreeMap<String, MenuNode> menuTree = new TreeMap<>();

        menuList.forEach(element -> {
            MenuNode menuNode = new MenuNode(element);
            menuMap.put(element.getCurrentKey(), menuNode);
            if (null == element.getParentKey() || "".equals(element.getParentKey())) {
                menuTree.put(element.getCurrentKey(), menuNode);
            }
        });

        menuList.forEach(element -> {
            if (null != element.getParentKey()) {
                MenuNode parentNode = menuMap.get(element.getParentKey());
                if (null != parentNode) {
                    List<MenuNode> children;
                    if (null == (children = parentNode.getChildren())) {
                        children = new ArrayList<>();
                        parentNode.setChildren(children);
                    }
                    children.add(menuMap.get(element.getCurrentKey()));
                }
            }
        });

        List<MenuNode> bak = new ArrayList<>(menuTree.values());
        bak.forEach(element -> element.sortChildrenOrder());
        MenuNode menuNode = new MenuNode(new Menu());
        menuNode.setChildren(bak);
        menuNode.sortChildrenOrder();
        return bak;
    }

    public List<Menu> findMenuByCurrentKey(String key) {
        List<Menu> menuList = menuRepository.findAllByCurrentKey(key);
        return menuList;
    }

    public List<Menu> findMenuByCurrentKeys(List<String> keyList) {
        List<Menu> menuList = menuRepository.findAllByCurrentKeyIn(keyList);
        return menuList;
    }

    public Menu findMenuById(Long id) {
        return menuRepository.findOne(id);
    }


    public boolean addMenu(Menu menu) {
        Menu bak = menuRepository.save(menu);
        if (null != bak) {
            return true;
        }
        return false;
    }

    //    @Cacheable(value = "MenuService_getTopMenu", key = "#user.id")
    public List<MenuItem> getUserMenu(UserDetails user) {
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        if (authorities == null || authorities.isEmpty()) {
            return Collections.emptyList();
        }

        List<Menu> menus = allMenu();
        if (menus == null || menus.isEmpty()) {
            return Collections.emptyList();
        }
        sortList(menus);
        List<MenuItem> collect = new ArrayList<>();
        menus.forEach(menu -> {
            if ((null == menu.getParentKey() || "".equals(menu.getParentKey()))
                    && authorities.contains(menu.getAuthorityEntity())) {
                collect.add(convertMenu2MenuItem(menu));
            }
        });
        return collect;
    }

    public List<MenuItem> getSubMenus(String parentKey) {
        List<Menu> allMenus = allMenu();
        List<MenuItem> requiredMenus = new ArrayList<>();
        allMenus.forEach(menu -> {
            if (parentKey.equals(menu.getParentKey())) {
                requiredMenus.add(convertMenu2MenuItem(menu));
            }
        });
        return requiredMenus;
    }

    public MenuItem convertMenu2MenuItem(Menu menu) {
        MenuItem menuItem = new MenuItem();
        menuItem.setUrl(menu.getUrl());
        menuItem.setTitle(menu.getLabelName());
        menuItem.setPicUrl(menu.getPicUrl());
        menuItem.setIcon(menu.getIcon());
        menuItem.setDetail(menu.getDescription());
        menuItem.setCode(menu.getCurrentKey());
        return menuItem;
    }

    public boolean deleteMenu(long id) {
        Menu menu = menuRepository.findOne(id);
        if (null != menu) {
            if (null != menu.getAuthorityEntity()) {
                // TODO 放开
//                if (0 != menu.getAuthorityEntity().getRoleEntities().size()) {
//                    return false;
//                } else {
//                    menuRepository.delete(id);
//                    logger.debug("delete menu whose id is {} successfully", id);
//                    return true;
//                }
            } else {
                logger.warn("the menu has no related authority, it's dirty data");
                menuRepository.delete(id);
                logger.debug("delete menu whose id is {} successfully", id);
                return true;
            }
        } else {
            logger.warn("the menu whose id is {} does not exist ", id);
        }
        return false;
    }
}
