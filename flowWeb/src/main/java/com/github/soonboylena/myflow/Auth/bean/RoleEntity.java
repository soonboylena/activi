package com.github.soonboylena.myflow.Auth.bean;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lungern xiii.at.cn@gmail.com
 * @date 2018/2/2
 */
@Entity
@Table(name = "role")
@DynamicUpdate
@Getter
@Setter
@RequiredArgsConstructor
public class RoleEntity extends BaseModel implements GrantedAuthority {

    private static final long serialVersionUID = -856234002396786101L;

    @Column(unique = true)
    private String authority;

    @NonNull
    @Column
    private String roleName;

    @Column
    private int flag;

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinTable(
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id")
    )
    private Set<AuthorityEntity> authorities = new HashSet<>();

    public RoleEntity(String authority, String roleName) {
        this.authority = authority;
        this.roleName = roleName;
    }

    public RoleEntity() {

    }

    public RoleEntity(long id, int flag, String roleName, String authority) {
        this.id = id;
        this.flag = flag;
        this.roleName = roleName;
        this.authority = authority;
    }
}
