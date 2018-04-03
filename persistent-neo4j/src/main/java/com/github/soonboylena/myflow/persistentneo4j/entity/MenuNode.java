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

//    @Relationship(type = "sub")
//    private List<MenuNode> subNode = new ArrayList<>();

    @Relationship
    private List<MenuItem> items = new ArrayList<>();

    public MenuNode() {
    }

    public void addItem(MenuItem item) {
        items.add(item);
        // TODO: 应该存id？
        item.setParentKey(this.getCurrentKey());
    }
}
