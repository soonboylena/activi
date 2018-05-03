package com.github.soonboylena.myflow.persistentneo4j.service;

import com.github.soonboylena.myflow.entity.custom.Permission;
import com.github.soonboylena.myflow.entity.custom.Role;
import com.github.soonboylena.myflow.entity.custom.User;
import com.github.soonboylena.myflow.framework.service.MflUserService;
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
    public void saveUser(User user) {

        LoginInfoEntity entity = new LoginInfoEntity();
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

        LoginInfoEntity save = loginInfoGraphRepository.save(entity);
    }
}
