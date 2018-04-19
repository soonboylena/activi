package com.github.soonboylena.myflow.entity.core;

import lombok.Data;

@Data
public abstract class AbstractMetaItem implements IMetaInput {

    private String key;
    //    private String name;
    private String caption;
    private String description;
    private MetaInputType type;

    public abstract MetaInputType getType();

    final void setType(MetaInputType type) {
    }
}
