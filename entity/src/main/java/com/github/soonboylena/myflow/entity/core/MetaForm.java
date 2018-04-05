package com.github.soonboylena.myflow.entity.core;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MetaForm extends MetaCollection<MetaField> {

    private String caption;
    private String businessKey;

    @Override
    public String getCaption() {
        return caption;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }
}
