package com.github.soonboylena.entity.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MetaForm {

    private String key;
    private Map<String, MetaItem> fields = new HashMap<>();

    public void addField(MetaItem metaItem) {
        fields.put(metaItem.getKey(), metaItem);
    }

    public Collection<MetaItem> getFields() {
        return fields.values();
    }

    public void setFields(Map<String, MetaItem> fields) {
        this.fields = fields;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
