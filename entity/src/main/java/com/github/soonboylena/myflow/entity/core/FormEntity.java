package com.github.soonboylena.myflow.entity.core;


import java.util.*;

public class FormEntity implements IEntity {

    private MetaForm metaCollection;
    private Map<String, Object> data = new HashMap<>(20);

    public FormEntity(MetaForm metaForm) {
        this.metaCollection = metaForm;
    }

    @Override
    public MetaForm getMeta() {
        return metaCollection;
    }

    @Override
    public Map<String, Object> getData() {
        return data;
    }

//    public void addEntity(IEntity entityObject) {
//        this.data.add(entityObject);
//    }

    public void setData(Map<String, Object> data) {
        this.data = Optional.ofNullable(data).orElse(new HashMap<>(20));
    }

    public List<FieldEntity> getFieldEntities() {
        List<FieldEntity> entities = new ArrayList<>(metaCollection.size());
        Map<String, MetaField> metaPool = metaCollection.getMetaPool();
        for (Map.Entry<String, MetaField> poolSet : metaPool.entrySet()) {
            String key = poolSet.getKey();
            Object o = data.get(key);
            FieldEntity<Object> field = new FieldEntity(poolSet.getValue(), o);
            field.setData(o);
            entities.add(field);
        }
        return entities;
    }

    public void addData(String key, Object o) {
        data.put(key, o);
    }
}
