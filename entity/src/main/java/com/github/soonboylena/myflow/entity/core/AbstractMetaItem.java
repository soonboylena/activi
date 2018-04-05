package com.github.soonboylena.myflow.entity.core;

import lombok.Data;

@Data
public class AbstractMetaItem implements IMetaInput {

    private String key;
    private String name;
    private String caption;
    private String description;
    private String type;
}
