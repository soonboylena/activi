package com.github.soonboylena.entity.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MetaForm implements IMeta {

    private String key;
    private Map<String, MetaField> fields = new LinkedHashMap<>();

    public void addField(MetaField metaField) {
        fields.put(metaField.getKey(), metaField);
    }

    public Collection<MetaField> getFields() {
        return fields.values();
    }

    public void setFields(Map<String, MetaField> fields) {
        this.fields = fields;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
