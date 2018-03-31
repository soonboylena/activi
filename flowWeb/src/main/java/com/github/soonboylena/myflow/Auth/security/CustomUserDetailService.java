package com.github.soonboylena.myflow.Auth.security;


import com.github.soonboylena.myflow.persistentneo4j.entity.LoginInfoEntity;
import com.github.soonboylena.myflow.persistentneo4j.repository.LoginInfoGraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lungern xiii.at.cn@gmail.com
 * @date 2018/2/2
 */


@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private LoginInfoGraphRepository loginInfoGraphRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        List<LoginInfoEntity> userByUserName = loginInfoGraphRepository.findByUsername(s);

        if (userByUserName == null || userByUserName.isEmpty()) {
            throw new UsernameNotFoundException(s);
        }
        if (userByUserName.size() != 1) {
            throw new RuntimeException("找到了同名的登录信息。登录名： " + s);
        }

        // TODO
        return null;
    }


}

