package com.github.soonboylena.myflow.Auth.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@DynamicUpdate
@Entity
@Table(name = "user")
@Getter
@Setter
public class UserEntity extends BaseModel implements UserDetails {
    public UserEntity() {
    }

    @Column(nullable = false)
    private String nickName;

    @Column(updatable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private boolean enabled = true;

    @ManyToMany(cascade = { CascadeType.DETACH }, fetch = FetchType.LAZY)
    @JoinTable(
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    @JsonIgnoreProperties(value = {"users"})
    private Set<RoleEntity> roles = new HashSet<>();


    public void addRole(RoleEntity role) {
        roles.add(role);
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        if (roles == null) return Collections.emptySet();

        Set<GrantedAuthority> rolesAndAuthors = new HashSet<>();
        for (RoleEntity role : roles) {
            List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList("ROLE_" + role.getAuthority());
            rolesAndAuthors.addAll(authorityList);

            Set<AuthorityEntity> authorities = role.getAuthorities();
            rolesAndAuthors.addAll(authorities);
        }
        return rolesAndAuthors;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


}
