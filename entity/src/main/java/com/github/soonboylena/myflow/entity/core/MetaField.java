package com.github.soonboylena.myflow.entity.core;

import lombok.Data;

@Data
public class MetaField implements IMetaInput {

    // 实际item
    private AbstractMetaItem metaItem;
    // 是否是只读
    private boolean readOnly = false;
    // 是否必填
    private boolean required = false;
    // 组件宽度
    private int rowSpan = 1;

    private String caption;
    private String description;
    private int sort = 0;

    public MetaField(AbstractMetaItem metaItem) {
        this.metaItem = metaItem;
    }

    public String getKey() {
        return getMetaItem().getKey();
    }

    public String getDescription() {
        return this.description == null ? getMetaItem().getDescription() : this.description;
    }

    public MetaInputType getType() {
        return getMetaItem().getType();
    }

    public void setKey(String key) {
        getMetaItem().setKey(key);
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setRowSpan(Integer rowSpan) {
        this.rowSpan = rowSpan;
    }
}
