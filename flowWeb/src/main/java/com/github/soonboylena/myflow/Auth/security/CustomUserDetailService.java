package com.github.soonboylena.myflow.Auth.security;


import com.github.soonboylena.myflow.Auth.bean.UserEntity;
import com.github.soonboylena.myflow.Auth.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author lungern xiii.at.cn@gmail.com
 * @date 2018/2/2
 */


@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        UserEntity userByUserName = userRepository.findUserByUsername(s);

        if (userByUserName == null) {
            throw new UsernameNotFoundException(s);
        }
        return userByUserName;
    }


}

