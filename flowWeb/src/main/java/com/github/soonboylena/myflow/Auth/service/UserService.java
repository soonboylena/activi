package com.github.soonboylena.myflow.Auth.service;

import com.github.soonboylena.myflow.Auth.bean.WebUser;
import com.github.soonboylena.myflow.entity.custom.Permission;
import com.github.soonboylena.myflow.entity.custom.Role;
import com.github.soonboylena.myflow.persistentneo4j.entity.AuthorityEntity;
import com.github.soonboylena.myflow.persistentneo4j.entity.LoginInfoEntity;
import com.github.soonboylena.myflow.persistentneo4j.repository.LoginInfoGraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lungern xiii.at.cn@gmail.com
 * @date 2018/2/2
 */

@Service
public class UserService {

//    @Value("${user.custom.authority}")
//    private String authority;


    @Autowired
    private LoginInfoGraphRepository loginInfoRepository;

    @Autowired
    private PasswordEncoder encryption;


    public List<LoginInfoEntity> findUserLikeName(String name) {
        return loginInfoRepository.findAllByUsernameLike(name);
    }

    public boolean exist(String username) {
        Boolean exists = loginInfoRepository.existsLoginInfoEntitiesByUsername(username);
        return exists != null && exists;
    }

    public LoginInfoEntity findUserById(Long id) {
        return loginInfoRepository.findById(id).orElseGet(null);
    }

    public LoginInfoEntity saveUser(WebUser user) {

        LoginInfoEntity entity = new LoginInfoEntity();
        entity.setTitle(user.getUserNickName());
        entity.setUsername(user.getUsername());
        entity.setPassword(encryption.encode(user.getPassword()));

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
        return loginInfoRepository.save(entity);
    }

    public LoginInfoEntity updateUser(LoginInfoEntity loginInfoEntity, boolean shouldEncrypt) {
        if (shouldEncrypt) {
            loginInfoEntity.setPassword(encryption.encode(loginInfoEntity.getPassword()));
        }
        return loginInfoRepository.save(loginInfoEntity);
    }

    public Iterable<LoginInfoEntity> listAllUser() {
        Iterable<LoginInfoEntity> all = loginInfoRepository.findAll();
        if (all != null) {
            all.forEach(LoginInfoEntity::getAuthorities);
        }
        return all;
    }
}
