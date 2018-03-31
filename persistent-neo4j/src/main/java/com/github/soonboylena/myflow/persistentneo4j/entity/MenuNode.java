package com.github.soonboylena.myflow.persistentneo4j.entity;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.stereotype.Service;

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

    private String currentKey;

    private String title;

    private String icon;

    private String description;

    private String parentKey;

    private String url;

    private String picUrl;

    private int flag;

    private AuthorityEntity authorityEntity;

    @Relationship(type = "sub")
    private List<MenuNode> children = new ArrayList<>();

    public MenuNode() {
    }

    public void addSubNode(MenuNode node) {
        children.add(node);
        // TODO: 应该存id？
        node.setParentKey(this.getCurrentKey());
    }
}
