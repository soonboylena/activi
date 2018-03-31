package com.github.soonboylena.myflow.menu;

import com.github.soonboylena.myflow.Auth.bean.Menu;
import com.github.soonboylena.myflow.Auth.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.RoleNotFoundException;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/module")
public class ModuleController {


    @Autowired
    private MenuService menuService;

    @RequestMapping("topMenu")
    public List<Menu> topMenu(@RequestParam(defaultValue = "") String systemKey, @AuthenticationPrincipal User user) {
        return menuService.getUserMenu(user);
    }

    @RequestMapping("leftMenu")
    public List<Menu> leftMenu(@RequestParam() String id) {
        return menuService.getSubMenus(id);
    }

    @RequestMapping("nickName")
    public String getUserNickName() {
        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }

    private static String getRole() throws RoleNotFoundException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = null;
        Iterator iterator = userDetails.getAuthorities().iterator();
        while (iterator.hasNext()) {
            GrantedAuthority next = (GrantedAuthority) iterator.next();
            role = next.getAuthority();
            break;
        }
        if (role == null) {
            throw new RoleNotFoundException();
        }
        return role;
    }
}

