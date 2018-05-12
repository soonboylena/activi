package com.github.soonboylena.myflow.entity.core;


import lombok.EqualsAndHashCode;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
public class MetaForm extends MetaCollection<MetaField> {

    private String caption;
    private Map<String, Relation> relations = new HashMap<>();
    private String description;


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

    public void setCaption(String caption) {
        this.caption = caption;
    }


    public Collection<Relation> getRelations() {
        return relations.values();
    }

    public boolean contains(String key) {
        return metaPool != null && metaPool.containsKey(key);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
