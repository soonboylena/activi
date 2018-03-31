package com.github.soonboylena.myflow.Auth.service;

import com.github.soonboylena.myflow.persistentneo4j.entity.LoginInfoEntity;
import com.github.soonboylena.myflow.persistentneo4j.repository.LoginInfoGraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
        boolean exists = loginInfoRepository.existsByUsername(username);
        return exists;
    }

    public LoginInfoEntity findUserById(Long id) {
        return loginInfoRepository.findOne(id);
    }

    public LoginInfoEntity saveUser(LoginInfoEntity user) {
        user.setPassword(encryption.encode(user.getPassword()));
//        if (user.getAuthorities() != null && user.getAuthorities().isEmpty()) {
//            RoleEntity roleEntity = loginInfoRepository.findRoleByRoleName(authority);
//            user.addRole(roleEntity);
//        }
//        return userRepository.save(user);
//        return null;
        LoginInfoEntity save = loginInfoRepository.save(user);
        return save;
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
