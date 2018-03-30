package com.github.soonboylena.myflow.Auth.service;

import com.github.soonboylena.myflow.Auth.bean.AuthorityEntity;
import com.github.soonboylena.myflow.Auth.jpa.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    public List<AuthorityEntity> findAllAuthority() {
        return authorityRepository.findAll();
    }

    public List<AuthorityEntity> findAuthoritiesInIds(List<Long> ids) {
        return authorityRepository.findAuthoritiesByIdIn(ids);
    }

    public List<AuthorityEntity> findAuthoritiesByAuthority(List<String> authorityKeys) {
        return authorityRepository.findAllByAuthorityIn(authorityKeys);
    }
}
