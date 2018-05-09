package com.github.soonboylena.myflow.Auth.security;


import com.github.soonboylena.myflow.Auth.bean.SecurityUserImpl;
import com.github.soonboylena.myflow.persistentneo4j.entity.AuthorityEntity;
import com.github.soonboylena.myflow.persistentneo4j.entity.LoginInfoEntity;
import com.github.soonboylena.myflow.persistentneo4j.repository.AuthorityGraphRepository;
import com.github.soonboylena.myflow.persistentneo4j.repository.LoginInfoGraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lungern xiii.at.cn@gmail.com
 * @date 2018/2/2
 */


@Component
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

        LoginInfoEntity loginInfoEntity = userByUserName.get(0);

        Set<AuthorityEntity> authorities = loginInfoEntity.getAuthorities();

        Set<String> authStrings = new HashSet<>();
        for (AuthorityEntity authority : authorities) {
            collectAuthString(authority, authStrings);
        }

        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(authStrings.toArray(new String[authStrings.size()]));

        SecurityUserImpl securityUser = new SecurityUserImpl(loginInfoEntity.getUsername(), loginInfoEntity.getPassword(), authorityList);
        securityUser.setId(loginInfoEntity.getId());
        return securityUser;
    }

    private void collectAuthString(AuthorityEntity authority, Set<String> authStrings) {
        if (authority != null) {
            authStrings.add(authority.getExpress());
        }
        Set<AuthorityEntity> authorities = authority.getAuthorities();

        if (authorities.isEmpty()) return;

        for (AuthorityEntity entity : authorities) {
            collectAuthString(entity, authStrings);
        }
    }


}

