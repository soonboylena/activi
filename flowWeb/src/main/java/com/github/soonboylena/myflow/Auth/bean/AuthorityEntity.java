package com.github.soonboylena.myflow.Auth.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * @author lungern xiii.at.cn@gmail.com
 * @date 2018/2/5
 */

@Entity
@Table(name = "authorityEntity")
@Getter
@Setter
@JsonIgnoreProperties(value = "roleEntities")
public class AuthorityEntity extends BaseModel implements GrantedAuthority {

    @NonNull
    @Column
    private String title;

    @NonNull
    @Column
    private String authority;

    @Column
    private String description;

    @OneToOne(mappedBy = "authorityEntity", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"authorityEntity"})
    private Menu menu;

    @Override
    public String getAuthority() {
        return authority;
    }

    @ManyToMany(mappedBy = "authorities")
    private Set<RoleEntity> roleEntities = new HashSet<>();

}
