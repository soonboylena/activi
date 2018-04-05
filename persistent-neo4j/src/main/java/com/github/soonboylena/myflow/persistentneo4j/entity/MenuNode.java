package com.github.soonboylena.myflow.persistentneo4j.entity;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lungern xiii.at.cn@gmail.com
 * @date 2018/2/2
 */
@NodeEntity
@Getter
@Setter
public class MenuNode extends BaseModel {

    private int menuOrder = 0;

    @Index(unique = true)
    private String currentKey;

    private String title;

    private String icon;

    private String parentKey;

    private String url;

    @Relationship
    private List<MenuNode> items = new ArrayList<>();

    @Relationship
    private AuthorityEntity authorityEntity;

    public MenuNode() {
    }

    public MenuNode(String currentKey, String parentKey, String title) {
        this.currentKey = currentKey;
        this.parentKey = parentKey;
        this.title = title;
    }

    public void addItem(MenuNode item) {
        items.add(item);
        item.setParentKey(this.getCurrentKey());
    }
}
