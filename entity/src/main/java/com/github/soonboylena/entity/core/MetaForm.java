package com.github.soonboylena.entity.core;

import lombok.Data;

import java.util.Map;

@Data
public class MetaForm {

    private String key;
    private Map<String, MetaItem> fields;
}
