package com.github.soonboylena.myflow.persistentneo4j.entity;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lungern xiii.at.cn@gmail.com
 * @date 2018/2/2
 */
@Getter
@Setter
@NodeEntity
public class RoleEntity extends BaseModel {

    private String authority;

    private String roleName;

//    private Set<LoginInfoEntity> users = new HashSet<>();

    private Set<AuthorityEntity> authorities = new HashSet<>();

    public RoleEntity(String authority, String roleName) {
        this.authority = authority;
        this.roleName = roleName;
    }

    public RoleEntity() {

    }

    public RoleEntity(long id, String roleName, String authority) {
        this.id = id;
        this.roleName = roleName;
        this.authority = authority;
    }

    public void addAuthority(AuthorityEntity auth) {
        authorities.add(auth);
    }
}
