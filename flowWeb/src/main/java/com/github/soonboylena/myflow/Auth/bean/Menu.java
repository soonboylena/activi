package com.github.soonboylena.myflow.Auth.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author lungern xiii.at.cn@gmail.com
 * @date 2018/2/2
 */

@Entity
@Table(name = "menu")
@Getter
@Setter
//@RequiredArgsConstructor
//@NoArgsConstructor
//@AllArgsConstructor
public class Menu extends BaseModel {

    @Column
    private int menuOrder = 0;

    @Column(unique = true)
    @NonNull
    private String currentKey;

    @Column
    @NonNull
    private String labelName;

    @Column
    private String icon;

    @Column
    private String description;

    @Column
    private String parentKey;

    @Column
    private String url;

    @Column
    private String picUrl;

    @Column
    private int flag;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "authority_menu",
            joinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id")
    )
    @JsonIgnoreProperties(value = {"menu"})
    private AuthorityEntity authorityEntity;

}
