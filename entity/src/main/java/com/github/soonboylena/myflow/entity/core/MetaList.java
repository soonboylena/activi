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

    //    public static MetaList fromMetaForm(MetaForm metaForm) {
//        this.metaForm = metaForm;

//        if (metaForm == null) {
//            return null;
//        }
//
//        MetaList list = new MetaList();
//        list.setMetaPool(metaForm.getMetaPool());
//        list.setBusinessKey(metaForm.getBusinessKey());
//        list.setKey(metaForm.getKey());
//        list.setCaption(metaForm.getCaption());
//        return list;
//    }

//    public void addDatum(Map<String, Object> datum) {
//        if (datum != null) {
//            data.add(datum);
//        }
//    }
//
//    public Map<String, Object> getDatum(int index) {
//
//        if (data.size() > index) {
//            return data.get(index);
//        }
//        return null;
//    }


}
