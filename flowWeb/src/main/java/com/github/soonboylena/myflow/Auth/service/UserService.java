package com.github.soonboylena.myflow.Auth.service;

import com.github.soonboylena.myflow.Auth.bean.WebUser;
import com.github.soonboylena.myflow.entity.custom.MflUser;
import com.github.soonboylena.myflow.framework.web.MflUserService;
import com.github.soonboylena.myflow.persistentneo4j.entity.LoginInfoEntity;
import com.github.soonboylena.myflow.persistentneo4j.repository.LoginInfoGraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private MflUserService userService;

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

    @Transactional
    public void saveUser(WebUser user) {

        MflUser user1 = new MflUser();
        user1.setUserNickName(user.getTitle());
        user1.setUsername(user.getUsername());
        user1.setPassword(encryption.encode(user.getPassword()));

        user1.setRoles(user.getAuthorities());

        userService.saveUser(user1);
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
