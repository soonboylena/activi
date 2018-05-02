package com.github.soonboylena.myflow.Auth.service;

import com.github.soonboylena.myflow.Auth.bean.WebRole;

import java.util.List;

public interface RoleService {
    WebRole saveRole(WebRole role);

    List<WebRole> findAllRoles();

    List<String> findRoleMenu(Long roleId);

    WebRole findRoleByRoleName(String roleType);

    WebRole findRoleById(Long id);
}
