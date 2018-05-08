package com.github.soonboylena.myflow.entity.core;


import java.util.Map;

/**
 * 一览的定义meta
 */
public class MetaList implements IMeta {

    private MetaForm metaForm;

    public MetaList(MetaForm metaForm) {
        this.metaForm = metaForm;
    }

    public String getCaption() {
        return metaForm.getCaption();
    }

    public MetaForm getMetaForm() {
        return metaForm;
    }

    @Override
    public String getKey() {
        return metaForm.getKey();
    }

    public Map<String, MetaField> getMetaPool() {
        return getMetaForm().getMetaPool();
    }

    public String getBusinessKey() {
        return metaForm.getBusinessKey();
    }



}
