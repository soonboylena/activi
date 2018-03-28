package com.github.soonboylena.entity.core;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MetaForm extends MetaCollection<MetaField> {

    private String caption;

    @Override
    public String getCaption() {
        return caption;
    }
}
