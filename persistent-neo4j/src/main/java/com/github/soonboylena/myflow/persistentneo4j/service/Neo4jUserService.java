package com.github.soonboylena.myflow.persistentneo4j.service;

import com.github.soonboylena.myflow.entity.custom.Permission;
import com.github.soonboylena.myflow.entity.custom.Role;
import com.github.soonboylena.myflow.entity.custom.User;
import com.github.soonboylena.myflow.framework.web.MflUserService;
import com.github.soonboylena.myflow.persistentneo4j.entity.AuthorityEntity;
import com.github.soonboylena.myflow.persistentneo4j.entity.LoginInfoEntity;
import com.github.soonboylena.myflow.persistentneo4j.repository.LoginInfoGraphRepository;

import java.util.HashSet;
import java.util.Set;

public class Neo4jUserService implements MflUserService {

    private LoginInfoGraphRepository loginInfoGraphRepository;

    public Neo4jUserService(LoginInfoGraphRepository loginInfoGraphRepository) {
        this.loginInfoGraphRepository = loginInfoGraphRepository;
    }

    @Override
    public User saveUser(User user) {

        LoginInfoEntity entity = user2LoginInfo(user);
        LoginInfoEntity save = loginInfoGraphRepository.save(entity);
        return loginInfo2User(save);
    }

    private User loginInfo2User(LoginInfoEntity save) {
        User user = new User();
        user.setId(save.getId());
        user.setUserNickName(save.getTitle());
        user.setUsername(save.getUsername());
        user.setPassword(save.getPassword());

        Set<AuthorityEntity> authorities = save.getAuthorities();
        if (authorities == null) return user;

        for (AuthorityEntity authority : authorities) {

            Role role = new Role();
            role.setId(authority.getId());
            role.setExpress(authority.getExpress());
            role.setTitle(authority.getTitle());
            role.setDescription(authority.getDescription());

            Set<AuthorityEntity> permissions = authority.getAuthorities();
            if (permissions != null) {
                for (AuthorityEntity permission : permissions) {
                    Permission p = new Permission();
                    p.setId(permission.getId());
                    p.setExpress(permission.getExpress());
                    p.setTitle(permission.getTitle());
                    p.setDescription(permission.getDescription());

                    role.addPermission(p);
                }
            }

            user.addRole(role);
        }
        return user;

    }

    private LoginInfoEntity user2LoginInfo(User user) {
        LoginInfoEntity entity = new LoginInfoEntity();
        entity.setId(user.getId());
        entity.setTitle(user.getUserNickName());
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());

        Set<Role> roles = user.getRoles();
        Set<AuthorityEntity> neoRoles = new HashSet<>(roles.size());
        for (Role role : roles) {
            AuthorityEntity neoRole = new AuthorityEntity(role.getTitle(), role.getExpress());
            neoRole.setId(role.getId());
            neoRole.setDescription(role.getDescription());

            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                AuthorityEntity neoPermission = new AuthorityEntity(permission.getTitle(), permission.getExpress());
                neoPermission.setId(permission.getId());
                neoPermission.setDescription(permission.getDescription());
            }
            neoRoles.add(neoRole);
        }
        entity.setAuthorities(neoRoles);
        return entity;
    }
}
