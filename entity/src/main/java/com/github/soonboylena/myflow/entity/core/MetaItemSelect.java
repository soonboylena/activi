package com.github.soonboylena.myflow.entity.core;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
/**
 * 单选框
 */
public class MetaItemSelect extends AbstractMetaItem {
    Map<String, String> options = new HashMap<>();

    public void addOption(String key, String stringValue) {
        options.put(key, stringValue);
    }
}
