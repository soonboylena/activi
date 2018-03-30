package com.github.soonboylena.myflow.Auth.jpa;

import com.github.soonboylena.myflow.persistentneo4j.entity.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *   @author lungern xiii.at.cn@gmail.com
 *   @date 2018/2/6
 *
 */

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Long> {
    List<AuthorityEntity> findAuthoritiesByIdIn(List<Long> ids);
    List<AuthorityEntity> findAllByAuthorityIn(List<String> authorityKeys);
}
