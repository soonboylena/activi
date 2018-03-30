package com.github.soonboylena.myflow.Auth.controller;

import com.github.soonboylena.myflow.Auth.bean.AuthorityEntity;
import com.github.soonboylena.myflow.Auth.bean.Menu;
import com.github.soonboylena.myflow.Auth.bean.Message;
import com.github.soonboylena.myflow.Auth.bean.RoleEntity;
import com.github.soonboylena.myflow.Auth.service.AuthorityService;
import com.github.soonboylena.myflow.Auth.service.MenuService;
import com.github.soonboylena.myflow.Auth.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lungern xiii.at.cn@gmail.com
 * @date 2018/2/5
 */

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private AuthorityService authorityService;

    @PostMapping("/add")
    public Message<Boolean> addNewRole(@RequestBody RoleEntity roleEntity) {


        RoleEntity bak = roleService.saveRole(roleEntity);

        if (null != bak) {
            return new Message<>("添加角色成功", true);
        } else {
            return new Message<>("添加角色失败", false);
        }
    }

    @GetMapping("/roles")
    public List<RoleEntity> getRoles() {
        List<RoleEntity> list = roleService.findAllRoles();
        return list;
    }

    @PostMapping("/menus/{role}")
    public List<String> getMenuListByRole(@PathVariable String role) {
        return roleService.findRoleMenu(role);
    }

    @PostMapping("/authority/renewal")
    public Message updateRoleAuthority(@RequestBody Map<String, Object> map) {
        List<Long> authorityIds = (List<Long>) map.get("authorityIds");
        Long roleId = (Long) map.get("roleId");
        List<AuthorityEntity> authorityList = authorityService.findAuthoritiesInIds(authorityIds);
        RoleEntity roleEntity = roleService.findRoleById(roleId);
        roleEntity.setAuthorities(new HashSet<>(authorityList));
        RoleEntity bak = roleService.saveRole(roleEntity);
        Message<String>  message = new Message<>();
        if (null != bak) {
            message.setCode(200);
            message.setDescription("角色权限更新成功");
        } else {
            message.setCode(200);
            message.setDescription("角色权限更新失败");
        }
        return message;
    }

    @PostMapping("/menus/update/{role}")
    public Message updateRoleMenuRelation(@PathVariable String role, @RequestBody List<String> menuList) {
        Assert.notNull(menuList, "menuList不能为空");

        List<Menu> menus = menuService.findMenuByCurrentKeys(menuList);

        Set<AuthorityEntity> authorityEntities = new HashSet<>();
        menus.forEach(element -> {
            if (element.getAuthorityEntity() != null) {
                authorityEntities.add(element.getAuthorityEntity());
            }
        });

        RoleEntity bak = roleService.findRoleByRoleName(role);
        bak.setAuthorities(authorityEntities);

        RoleEntity tmp = roleService.saveRole(bak);

        Message message;
        if (null == tmp) {
            message = new Message(500, "服务器出错, 更新失败");
        } else {
            message = new Message(200, "更新成功");
        }

        return message;
    }

    @PostMapping("/update")
    public Message<Boolean> updateRole(@RequestBody RoleEntity roleEntity) {
        if ("".equals(roleEntity.getId())) {
            return new Message<>("角色id不能为空", false);
        }
        RoleEntity temp = roleService.findRoleById(roleEntity.getId());
        roleEntity.setAuthorities(temp.getAuthorities());
        roleEntity.setUsers(temp.getUsers());
        RoleEntity bak = roleService.saveRole(roleEntity);
        if (bak == null) {
            return new Message<>("更新失败", false);
        } else {
            return new Message<>("更新成功", true);
        }
    }
}