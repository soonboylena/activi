package com.github.soonboylena.myflow.Auth.controller;

import com.github.soonboylena.myflow.Auth.bean.WebRole;
import com.github.soonboylena.myflow.persistentneo4j.entity.AuthorityEntity;
import com.github.soonboylena.myflow.Auth.bean.Message;
import com.github.soonboylena.myflow.Auth.service.AuthorityService;
import com.github.soonboylena.myflow.Auth.service.MenuService;
import com.github.soonboylena.myflow.Auth.service.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author lungern xiii.at.cn@gmail.com
 * @date 2018/2/5
 */

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private AuthorityService authorityService;

    @PostMapping("/add")
    public Message<Boolean> addNewRole(@RequestBody WebRole role) {

        WebRole bak = roleService.saveRole(role);

        if (null != bak) {
            return new Message<>("添加角色成功", true);
        } else {
            return new Message<>("添加角色失败", false);
        }
    }

    @GetMapping("/roles")
    public List<WebRole> getRoles() {
        List<WebRole> list = roleService.findAllRoles();
        return list;
    }

    @GetMapping("/{id:\\d+}/menus")
    public List<String> getMenuListByRole(@PathVariable("id") Long roleId) {
        return roleService.findRoleMenu(roleId);
    }


    @PostMapping("/authority/renewal")
    public Message updateRoleAuthority(@RequestBody Map<String, Object> map) {
        List<Long> authorityIds = (List<Long>) map.get("authorityIds");
        Long roleId = (Long) map.get("roleId");
        List<AuthorityEntity> authorityList = authorityService.findAuthoritiesInIds(authorityIds);
        WebRole roleEntity = roleService.findRoleById(roleId);
        // TODO
//        roleEntity.setAuthorities(new HashSet<>(authorityList));
        WebRole bak = roleService.saveRole(roleEntity);
        Message<String> message = new Message<>();
        if (null != bak) {
            message.setCode(200);
            message.setDescription("角色权限更新成功");
        } else {
            message.setCode(200);
            message.setDescription("角色权限更新失败");
        }
        return message;
    }

    @PostMapping("/{id:\\d+}/menus")
    public Message updateRoleMenuRelation(@PathVariable("id") Long roleId, @RequestBody List<Long> authIds) {

        Assert.notNull(authIds, "menuList不能为空");
        AuthorityEntity entity = roleService.updateRoleWithPermissions(roleId, authIds);
        assert entity != null;
        return new Message(200, "更新成功！");
    }

    @PostMapping("/update")
    public Message<Boolean> updateRole(@RequestBody WebRole role) {
//        if ("".equals(role.getId())) {
//            return new Message<>("角色id不能为空", false);
//        }
//        WebRole temp = roleService.findRoleById(role.getId());
//        role.setAuthorities(temp.getAuthorities());
////        roleEntity.setUsers(temp.getUsers());
//        RoleEntity bak = roleService.saveRole(roleEntity);
//        if (bak == null) {
//            return new Message<>("更新失败", false);
//        } else {
//            return new Message<>("更新成功", true);
//        }
        return null;
    }
}
