package com.github.soonboylena.myflow.entity.custom;

import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 用户实体
 */
@Data
public class User {

    private Long id;

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

    // 其他的用户属性
    Map<String, Object> otherProperties = new HashMap<>();

    public void set(String key, Object propertyValue) {
        otherProperties.put(key, propertyValue);
    }

    public Object get(String key) {
        return otherProperties.get(key);
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }
}
