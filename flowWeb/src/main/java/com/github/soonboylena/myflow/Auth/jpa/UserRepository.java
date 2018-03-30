package com.github.soonboylena.myflow.Auth.jpa;

import com.github.soonboylena.myflow.persistentneo4j.entity.LoginInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author lungern xiii.at.cn@gmail.com
 * @date 2018/2/2
 */

public interface UserRepository extends JpaRepository<LoginInfoEntity, Long> {

    LoginInfoEntity findUserById(Long userId);

    LoginInfoEntity findUserByUsername(String name);

    List<LoginInfoEntity> findUserByUsernameContains(String name);

    List<LoginInfoEntity> findAllByEnabledTrue();

}
