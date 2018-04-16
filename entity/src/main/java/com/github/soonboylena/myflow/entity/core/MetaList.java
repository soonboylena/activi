package com.github.soonboylena.myflow.entity.core;


/**
 * 一览的定义meta
 */
public class MetaList extends MetaCollection<MetaField> {

    private String caption;

    @Override
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public static MetaList fromMetaForm(MetaForm metaForm) {

        if (metaForm == null) {
            return null;
        }

        MetaList list = new MetaList();
        list.setMetaPool(metaForm.getMetaPool());
        list.setBusinessKey(metaForm.getBusinessKey());
        list.setKey(metaForm.getKey());
        list.setCaption(metaForm.getCaption());
        return list;
    }
}
