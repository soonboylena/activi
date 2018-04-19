package com.github.soonboylena.myflow.entity.core;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 选择一个资源
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class MetaItemResource extends AbstractMetaItem {

    private String key;

    @Override
    public MetaInputType getType() {
        return MetaInputType.resource;
    }
}
