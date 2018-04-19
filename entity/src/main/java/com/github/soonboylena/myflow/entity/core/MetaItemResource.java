package com.github.soonboylena.myflow.entity.core;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 选择一个资源
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class MetaItemResource extends AbstractMetaItem {

    private String key;

    @Override
    public MetaInputType getType() {
        return MetaInputType.select_form;
    }
}
