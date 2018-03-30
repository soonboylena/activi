package com.github.soonboylena.myflow.vModel.uiAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompRelationAction {

    // 组件自身id
    private String id;
    // 是否与其他组件关联
    private boolean isRelated;
    // 关联组件id list
    private List<String> relation;
    // attrLink
    private List<AttrLink> dataLink;

    public CompRelationAction(String id, boolean isRelated, List<String> relation) {
        this.id = id;
        this.isRelated = isRelated;
        this.relation = relation;
    }

    public void addDataLink(String attr, UrlObject link) {
        if (null == dataLink) {
            dataLink = new ArrayList<>();
        }
        dataLink.add(new AttrLink(attr, link));
    }

    @Data
    public class AttrLink {
        String attr;
        UrlObject link;

        AttrLink(String attr, UrlObject link) {
            this.attr = attr;
            this.link = link;
        }
    }
}
