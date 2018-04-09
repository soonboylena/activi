package com.github.soonboylena.myflow.entity.core;

public class FieldEntity<D> implements IEntity {

    private MetaField metaField;
    private D data;

    public FieldEntity(MetaField metaField, D data) {
        this.metaField = metaField;
        this.data = data;
    }

    @Override
    public MetaField getMeta() {
        return metaField;
    }

    @Override
    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }
}
