//package com.github.soonboylena.myflow.entity.core;
//
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class ViewEntity implements IEntity {
//
//    private MetaView metaCollection;
//    private Map<String, Map<String, Object>> data = new HashMap<>();
//
//    public ViewEntity(MetaView metaForm) {
//        this.metaCollection = metaForm;
//    }
//
//    @Override
//    public MetaView acquireMeta() {
//        return metaCollection;
//    }
//
//    @Override
//    public Map<String, Map<String, Object>> getData() {
//        return data;
//    }
//
//    public List<FormEntity> getSubFormEntities() {
//        List<FormEntity> entities = new ArrayList<>(metaCollection.size());
//
//        Map<String, MetaForm> metaPool = metaCollection.getMetaPool();
//        for (Map.Entry<String, MetaForm> poolSet : metaPool.entrySet()) {
//            String key = poolSet.getKey();
//            Map<String, Object> o = data.get(key);
//
//            FormEntity form = new FormEntity(poolSet.getValue());
//            form.setData(o);
//            entities.add(form);
//        }
//        return entities;
//    }
//
//    public void addDatum(String key, Map<String, Object> data) {
//        this.data.put(key, data);
//    }
//}
