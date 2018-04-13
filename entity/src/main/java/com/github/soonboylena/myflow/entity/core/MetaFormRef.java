package com.github.soonboylena.myflow.entity.core;


import lombok.EqualsAndHashCode;

import java.util.Collection;
import java.util.Map;

@EqualsAndHashCode(callSuper = false)
public class MetaFormRef extends MetaForm {

    private String caption;
    private MetaForm form;

    public MetaFormRef(MetaForm form) {
        this.form = form;
    }

    public MetaFormRef(String caption, MetaForm form) {
        this.caption = caption;
        this.form = form;
    }

    @Override
    public Collection<Relation> getRelations() {
        return form.getRelations();
    }

    @Override
    public Map<String, MetaField> getMetaPool() {
        return form.getMetaPool();
    }

    @Override
    public String getBusinessKey() {
        return form.getBusinessKey();
    }

    @Override
    public String getKey() {
        return form.getKey();
    }

    @Override
    public MetaField getMeta(String metaKey) {
        return form.getMeta(metaKey);
    }

    @Override
    public Collection<MetaField> getMetas() {
        return form.getMetas();
    }

    @Override
    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public String toString() {
        return form.toString();
    }

    @Override
    public String getCaption() {
        return this.caption == null ? form.getCaption() : this.caption;
    }

}
