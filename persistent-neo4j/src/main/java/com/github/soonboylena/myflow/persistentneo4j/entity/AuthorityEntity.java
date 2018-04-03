package com.github.soonboylena.myflow.persistentneo4j.entity;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.Labels;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * @author lungern xiii.at.cn@gmail.com
 * @date 2018/2/5
 */

@Getter
@NodeEntity
public class AuthorityEntity extends BaseModel {

    private String title;
    @Index
    private String express;
    private String description;

    public AuthorityEntity(String express) {
        this.express = express;
        super.setLabels(Collections.singleton(express.startsWith("ROLE_") ? "role" : "permission"));
    }

    public AuthorityEntity() {
    }

    @Relationship(type = "include")
    private Set<AuthorityEntity> authorities = new HashSet<>();

    public AuthorityEntity(String title, String express) {
        this.title = title;
        this.express = express;
    }

    public void addAuthority(AuthorityEntity entity) {
        authorities.add(entity);
    }


    @Override
    public void setLabels(Set<String> labels) {
        super.setLabels(labels);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }
}
