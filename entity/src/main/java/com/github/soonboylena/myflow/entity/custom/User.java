package com.github.soonboylena.myflow.entity.custom;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户实体
 */
@Data
public class User {

    // 登录名
    private String username;

    // 密码
    private String password;

    // 帐户可用
    private boolean enabled = true;

    // 名称
    private String userNickName;

    // 未超期
    private boolean accountNonExpired;

    // 未锁
    private boolean accountNonLocked;

    // 密码未超期
    private boolean credentialsNonExpired;

    private Set<Role> roles = new HashSet<>();

}
