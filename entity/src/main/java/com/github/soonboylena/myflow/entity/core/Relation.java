package com.github.soonboylena.myflow.entity.core;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Relation {

    String type;
    List<MetaForm> relatedForm = new ArrayList<>();

    Relation(String type) {
        this.type = type;
    }

    public void addRelatedForm(MetaForm metaForm) {
        relatedForm.add(metaForm);
    }
}
