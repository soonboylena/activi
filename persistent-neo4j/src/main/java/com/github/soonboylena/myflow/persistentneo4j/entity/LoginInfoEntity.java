package com.github.soonboylena.myflow.persistentneo4j.entity;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
@Getter
@Setter
public class LoginInfoEntity extends BaseModel {

    private String nickName;

    private String username;

    private String password;

    private boolean enabled = true;

    // 未超期
    private boolean accountNonExpired;

    // 未锁
    private boolean accountNonLocked;

    // 密码未超期
    private boolean credentialsNonExpired;

    @Relationship(type = "has", direction = Relationship.INCOMING)
    private Set<AuthorityEntity> authorities = new HashSet<>();


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }


}
