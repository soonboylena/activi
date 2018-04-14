package com.github.soonboylena.myflow.entity.core;


import lombok.EqualsAndHashCode;

import java.util.*;

@EqualsAndHashCode(callSuper = true)
public class MetaForm extends MetaCollection<MetaField> {

    private String caption;
    private Map<String, Relation> relations = new HashMap<>();

    /**
     * 如果这个form是relation里边的form，可能会有key重复的情况；
     * 加了index避免在画面上key值相同
     */
    private Integer index;

    @Override
    public String getCaption() {
        return caption;
    }

    public void setRelation(String type, MetaForm relatedForm) {
        Relation relation = relations.get(type);
        if (relation == null)
            relation = new Relation(type);
        relation.addRelatedForm(relatedForm);
        relations.put(type, relation);
    }

    public String getKeyIndex() {
        if (index == null || index == 0) {
            return getKey();
        }
        return getKey() + "[" + index + "]";
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Collection<Relation> getRelations() {
        return relations.values();
    }

    public boolean contains(String key) {
        return metaPool != null && metaPool.containsKey(key);
    }
}
