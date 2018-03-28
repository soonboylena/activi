package com.github.soonboylena.entity.core;

public class FieldEntity<D> implements IEntity {

    private MetaField metaField;
    private D data;

    public FieldEntity(MetaField metaField, D data) {
        this.metaField = metaField;
        this.data = data;
    }

    @Override
    public IMeta getMeta() {
        return metaField;
    }

    @Override
    public D getData() {
        return data;
    }
}
