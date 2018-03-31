package com.github.soonboylena.myflow.Auth.service;

import com.github.soonboylena.myflow.Auth.bean.Role;

import java.util.List;

public interface RoleService {
    Role saveRole(Role role);

    List<Role> findAllRoles();

    List<String> findRoleMenu(Long roleId);

    Role findRoleByRoleName(String roleType);

    Role findRoleById(Long id);
}
