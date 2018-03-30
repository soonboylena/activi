package com.github.soonboylena.myflow.Auth.jpa;

import com.github.soonboylena.myflow.Auth.bean.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author lungern xiii.at.cn@gmail.com
 * @date 2018/2/2
 */

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findUserById(Long userId);

    UserEntity findUserByUsername(String name);

    List<UserEntity> findUserByUsernameContains(String name);

    List<UserEntity> findAllByEnabledTrue();

}
