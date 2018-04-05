package com.github.soonboylena.myflow.entity.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class FormEntity implements IEntity {

    private final static Logger logger = LoggerFactory.getLogger(FormEntity.class);

    private MetaForm metaCollection;
    private List<IEntity> datas;

    public FormEntity(MetaForm metaForm) {
        this.metaCollection = metaForm;
        datas = new ArrayList<>(metaForm.size());
    }

    @Override
    public MetaForm getMeta() {
        return metaCollection;
    }

    @Override
    public List<IEntity> getData() {
        return datas;
    }

    public void addEntity(IEntity entityObject) {
        this.datas.add(entityObject);
    }
}
