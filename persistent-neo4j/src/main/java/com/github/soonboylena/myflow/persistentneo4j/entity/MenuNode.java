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

    private String title;

    private String icon;

    private String url;

    // 层级
    private int level = 1;

    @Relationship
    private List<MenuNode> items = new ArrayList<>();

    @Relationship
    private AuthorityEntity authorityEntity;

    public MenuNode() {
    }

    public MenuNode(String title) {
        this.title = title;
    }

    public void addItem(MenuNode item) {
        items.add(item);
        item.setLevel(this.level + 1);
    }

    private void setLevel(int level) {
        this.level = level;
    }
}
