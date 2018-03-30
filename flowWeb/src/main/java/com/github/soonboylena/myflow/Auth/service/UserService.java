package com.github.soonboylena.myflow.Auth.service;

import com.github.soonboylena.myflow.Auth.bean.RoleEntity;
import com.github.soonboylena.myflow.Auth.bean.UserEntity;
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

    public List<UserEntity> findUserLikeName(String name) {
        return userRepository.findUserByUsernameContains(name);
    }

    public boolean findIfExist(String username) {
        UserEntity user = userRepository.findUserByUsername(username);
        return null != user;
    }

    public UserEntity findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    public UserEntity saveUser(UserEntity user) {
        user.setPassword(encryption.encode(user.getPassword()));
        if (user.getRoles().size() == 0) {
            RoleEntity roleEntity = roleService.findRoleByRoleName(authority);
            user.addRole(roleEntity);
        }
        return userRepository.save(user);
    }

    public UserEntity updateUser(UserEntity user) {
        if (null != user.getPassword())
            user.setPassword(encryption.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public UserEntity updateUser(UserEntity userEntity, boolean shouldEncrypt) {
        if (shouldEncrypt) {
            userEntity.setPassword(encryption.encode(userEntity.getPassword()));
        }
        return userRepository.save(userEntity);
    }

    public List<UserEntity> listAllUser() {
        List<UserEntity> all = userRepository.findAllByEnabledTrue();
        if (all != null) {
            all.forEach(UserEntity::getAuthorities);
        }
        return all;
    }
}
