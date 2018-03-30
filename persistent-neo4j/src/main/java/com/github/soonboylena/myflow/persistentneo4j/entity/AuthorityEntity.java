package com.github.soonboylena.myflow.persistentneo4j.entity;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.NodeEntity;


/**
 * @author lungern xiii.at.cn@gmail.com
 * @date 2018/2/5
 */

@Getter
@Setter
@NodeEntity
public class AuthorityEntity extends BaseModel {

    private String title;
    private String authority;
    private String description;

    public String getAuthority() {
        return authority;
    }
}
