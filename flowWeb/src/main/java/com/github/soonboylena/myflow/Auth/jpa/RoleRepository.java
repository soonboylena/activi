package com.github.soonboylena.myflow.Auth.jpa;

import com.github.soonboylena.myflow.persistentneo4j.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * @author lungern xiii.at.cn@gmail.com
 * @date 2018/2/6
 */

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    List<RoleEntity> findAllByAuthoritiesLike(String name);

    RoleEntity findRoleByAuthority(String roleType);

    List<RoleEntity> findRolesByIdIsIn(List<Long> idList);
}
