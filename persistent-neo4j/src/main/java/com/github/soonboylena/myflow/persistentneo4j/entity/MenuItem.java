package com.github.soonboylena.myflow.persistentneo4j.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
@Getter
@Setter
@NoArgsConstructor
public class MenuItem extends BaseModel {


    private String currentKey;
    private String parentKey;
    private String title;
    private String url;
    private String icon;
    private String picUrl;
    private String description;

    @Relationship
    private AuthorityEntity authorityEntity;

    public MenuItem(String currentKey, String parentKey, String title) {
        this.currentKey = currentKey;
        this.parentKey = parentKey;
        this.title = title;
    }
}
