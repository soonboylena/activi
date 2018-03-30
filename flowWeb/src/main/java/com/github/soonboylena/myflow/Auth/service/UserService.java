package com.github.soonboylena.myflow.Auth.service;

import com.github.soonboylena.myflow.persistentneo4j.entity.LoginInfoEntity;
import com.github.soonboylena.myflow.persistentneo4j.entity.RoleEntity;
import com.github.soonboylena.myflow.Auth.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lungern xiii.at.cn@gmail.com
 * @date 2018/2/2
 */

@Service
public class UserService {

    @Value("${user.custom.authority}")
    private String authority;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encryption;

    public List<LoginInfoEntity> findUserLikeName(String name) {
        return userRepository.findUserByUsernameContains(name);
    }

    public boolean findIfExist(String username) {
        LoginInfoEntity user = userRepository.findUserByUsername(username);
        return null != user;
    }

    public LoginInfoEntity findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    public LoginInfoEntity saveUser(LoginInfoEntity user) {
        user.setPassword(encryption.encode(user.getPassword()));
        if (user.getRoles().size() == 0) {
            RoleEntity roleEntity = roleService.findRoleByRoleName(authority);
            user.addRole(roleEntity);
        }
        return userRepository.save(user);
    }

    public LoginInfoEntity updateUser(LoginInfoEntity user) {
        if (null != user.getPassword())
            user.setPassword(encryption.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public LoginInfoEntity updateUser(LoginInfoEntity loginInfoEntity, boolean shouldEncrypt) {
        if (shouldEncrypt) {
            loginInfoEntity.setPassword(encryption.encode(loginInfoEntity.getPassword()));
        }
        return userRepository.save(loginInfoEntity);
    }

    public List<LoginInfoEntity> listAllUser() {
        List<LoginInfoEntity> all = userRepository.findAllByEnabledTrue();
        if (all != null) {
            all.forEach(LoginInfoEntity::getAuthorities);
        }
        return all;
    }
}
