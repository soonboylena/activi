package com.github.soonboylena.entity.core;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class MetaForm {

    private String key;
    private Map<String, MetaItem> fields = new HashMap<>();


    public void addField(MetaItem metaItem) {
        fields.put(metaItem.getKey(), metaItem);
    }
}
