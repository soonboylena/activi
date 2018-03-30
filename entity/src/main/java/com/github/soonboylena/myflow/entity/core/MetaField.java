package com.github.soonboylena.myflow.entity.core;

import lombok.Data;

@Data
public class MetaField implements IMetaInput {

    private MetaItem metaItem;
    private boolean readOnly = false;
    private boolean required = false;

    private String caption;
    private int sort = 0;

    public MetaField(MetaItem metaItem) {
        this.metaItem = metaItem;
    }

    public String getKey() {
        return getMetaItem().getKey();
    }

    public String getName() {
        return getMetaItem().getName();
    }

    public String getDescription() {
        return getMetaItem().getDescription();
    }

    public String getType() {
        return getMetaItem().getType();
    }

    public void setKey(String key) {
        getMetaItem().setKey(key);
    }

    public void setName(String name) {
        getMetaItem().setName(name);
    }

    public void setDescription(String description) {
        getMetaItem().setDescription(description);
    }

    public void setType(String type) {
        getMetaItem().setType(type);
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getCaption() {
        return this.caption == null ? getMetaItem().getCaption() : this.caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
