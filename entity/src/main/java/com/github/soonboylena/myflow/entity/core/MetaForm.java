package com.github.soonboylena.myflow.entity.core;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class MetaForm extends MetaCollection<MetaField> {

    private String caption;
    private List<Relation> relations = new ArrayList<>();

    @Override
    public String getCaption() {
        return caption;
    }

    public void setRelation(String type, MetaForm relatedForm) {
        Relation r = new Relation(type, relatedForm);
        relations.add(r);
    }

    @Data
    public static class Relation {
        String type;
        MetaForm relatedForm;

        Relation(String type, MetaForm relatedForm) {
            this.type = type;
            this.relatedForm = relatedForm;
        }
    }
}
