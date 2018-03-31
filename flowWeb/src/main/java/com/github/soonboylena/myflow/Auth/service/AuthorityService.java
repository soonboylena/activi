package com.github.soonboylena.myflow.Auth.service;

import com.github.soonboylena.myflow.persistentneo4j.entity.AuthorityEntity;
import com.github.soonboylena.myflow.persistentneo4j.repository.AuthorityGraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityGraphRepository authorityRepository;

    public List<AuthorityEntity> findAllAuthority() {
//        return authorityRepository.findAll();
        return null;
    }

    public List<AuthorityEntity> findAuthoritiesInIds(List<Long> ids) {
//        return authorityRepository.findAuthoritiesByIdIn(ids);
        return null;
    }

    public List<AuthorityEntity> findAuthoritiesByAuthority(List<String> authorityKeys) {
//        return authorityRepository.findAllByAuthorityIn(authorityKeys);
        return null;
    }
}
