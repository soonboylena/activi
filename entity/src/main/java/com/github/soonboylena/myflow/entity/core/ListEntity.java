package com.github.soonboylena.myflow.entity.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListEntity implements IEntity {

    private MetaList meta;
    private List<Map<String, Object>> data = new ArrayList<>();

    public ListEntity(MetaList metaList) {
        this.meta = metaList;
    }

    @Override
    public MetaList acquireMeta() {
        return meta;
    }

    @Override
    public List<Map<String, Object>> getData() {
        return data;
    }


    public void addDatum(Map<String, Object> datum) {
        if (datum != null) {
            data.add(datum);
        }
    }

    /**
     * 把ListEntity转成 FormEntity的数组
     */
    public List<FormEntity> getFormEntities() {
        MetaForm metaForm = meta.getMetaForm();
        List<FormEntity> entities = new ArrayList<>(data.size());

        for (Map<String, Object> datum : data) {

            FormEntity entity = new FormEntity(metaForm);
            entity.setData(datum);
            entities.add(entity);
        }
        return entities;
    }
}
