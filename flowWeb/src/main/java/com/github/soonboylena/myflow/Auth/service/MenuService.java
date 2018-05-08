package com.github.soonboylena.myflow.Auth.service;

import com.github.soonboylena.myflow.Auth.bean.Menu;
import com.github.soonboylena.myflow.entity.custom.Permission;
import com.github.soonboylena.myflow.persistentneo4j.entity.AuthorityEntity;
import com.github.soonboylena.myflow.persistentneo4j.entity.MenuNode;
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

    public List<Menu> allMenu() {
        // TODO ALL MENU
        List<MenuNode> menuList = menuRepository.findAllMenuNodeAndItem();
        return menuList.stream()
//                .filter(m -> m.getAuthorityEntity() == null) // TODO 去掉重复的那些接点。这个需要改findAllMenuNodeAndItem方法；
                .map(this::fromDb)
                .filter(m -> !(m.getParentId() != null && m.getAuth() != null))
                .collect(Collectors.toList());
    }

//    public boolean existsByCurrentKey(String currentKey) {
//        return menuRepository.existsByCurrentKey(currentKey);
//    }


    public List<Menu> getUserMenu(UserDetails user) {
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        if (authorities == null || authorities.isEmpty()) {
            return Collections.emptyList();
        }

        Set<String> collect = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        List<MenuNode> menus = menuRepository.findLevel2MenuByExpress(collect);
        if (menus == null || menus.isEmpty()) {
            return Collections.emptyList();
        }
        return menus.stream().map(this::fromDb).collect(Collectors.toList());
    }


    public List<Menu> getSubMenus(Long parentId, User user) {

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        if (authorities == null || authorities.isEmpty()) {
            return Collections.emptyList();
        }

        Set<String> collect = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        if (logger.isDebugEnabled()) {
            logger.debug("左边menu的id： {}", parentId);
            logger.debug("-- 当前用户权限");
            for (String s : collect) {
                logger.debug("--- {}", s);
            }
        }

//        List<MenuItem> menuByParentKeyAndExpress = menuItemGR.findMenuByParentKeyAndExpress(parentKey, collect);
//        return menuByParentKeyAndExpress.stream().map(this::fromDb).collect(Collectors.toList());

        List<MenuNode> menuByExpress = menuRepository.findMenuByExpress(collect, parentId);
        return menuByExpress.stream().map(this::fromDb).collect(Collectors.toList());
    }


    public void deleteMenu(long id) {
        menuRepository.deleteById(id);
    }


    public void updateMenu(Menu menu) {
        MenuNode menuNode = toDb(menu);
        menuRepository.save(menuNode);
    }

    private MenuNode toDb(Menu menu) {

        MenuNode node = new MenuNode();
        node.setId(menu.getId());
        node.setUrl(menu.getUrl());
        node.setTitle(menu.getTitle());
        node.setIcon(menu.getIcon());
        List<Menu> items = menu.getChildren();
        if (items != null) {
            for (Menu item : items) {
                node.addItem(toDb(item));
            }
        }

        Permission auth = menu.getAuth();
        if (auth != null) {
            node.setAuthorityEntity(toDb(auth));
        }
        return node;
    }

    private AuthorityEntity toDb(Permission per) {
        AuthorityEntity auth = new AuthorityEntity();
        auth.setId(per.getId());
        auth.setExpress(per.getExpress());
        auth.setTitle(per.getTitle());
        auth.setDescription(per.getDescription());
        return auth;
    }

    private Menu fromDb(MenuNode menuNode) {
        Menu menu = new Menu();
        menu.setUrl(menuNode.getUrl());
        menu.setTitle(menuNode.getTitle());
        menu.setIcon(menuNode.getIcon());
        menu.setId(menuNode.getId());

        AuthorityEntity authorityEntity = menuNode.getAuthorityEntity();
        if (authorityEntity != null) {
            menu.setAuth(fromDb(authorityEntity));
        }

        List<MenuNode> items = menuNode.getItems();
        if (items == null) {
            return menu;
        }

        for (MenuNode item : items) {
            menu.addSubMenu(fromDb(item));
        }


        return menu;
    }

    private Permission fromDb(AuthorityEntity authorityEntity) {
        Permission p = new Permission();
        p.setId(authorityEntity.getId());
        p.setDescription(authorityEntity.getDescription());
        p.setExpress(authorityEntity.getExpress());
        p.setTitle(authorityEntity.getTitle());
        return p;
    }


}
