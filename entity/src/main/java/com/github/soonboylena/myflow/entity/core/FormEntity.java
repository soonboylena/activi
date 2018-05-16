package com.github.soonboylena.myflow.entity.core;


import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class FormEntity implements IEntity {

    private MetaForm metaCollection;
    private Map<String, Object> data = new HashMap<>(20);


    // key值是关系类型；一个关系类型底下可能会有多个相关的entity
    private Map<String, List<FormEntity>> relations = new HashMap<>();

    public FormEntity(MetaForm metaForm) {
        this.metaCollection = metaForm;
    }

    @Override
    public MetaForm acquireMeta() {
        return metaCollection;
    }

    @Override
    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = Optional.ofNullable(data).orElse(new HashMap<>(20));
    }

    public List<FieldEntity> getFieldEntities() {
        List<FieldEntity> entities = new ArrayList<>(metaCollection.size());
        Map<String, MetaField> metaPool = metaCollection.getMetaPool();
        for (Map.Entry<String, MetaField> poolSet : metaPool.entrySet()) {
            String key = poolSet.getKey();
            Object o = data.get(key);
            FieldEntity<Object> field = new FieldEntity(poolSet.getValue(), o);
            field.setData(o);
            entities.add(field);
        }
        return entities;
    }

    public void addData(String key, Object o) {
        data.put(key, o);
    }

    public void addRelatedForm(String type, FormEntity nextFormEntity) {
        List<FormEntity> entities = relations.get(type);
        // 如果没有这种类型的关系，建一个
        if (entities == null) entities = new ArrayList<>();
        entities.add(nextFormEntity);
        relations.put(type, entities);
    }

    public List<FormEntity> getRelations(String type) {
        List<FormEntity> entities = relations.get(type);
        if (entities == null) return Collections.emptyList();
        return entities;
    }

    public void setDatum(String key, Object property) {
        if (metaCollection.contains(key)) {
            data.put(key, property);
            return;
        }

        throw new RuntimeException("metaPool 中没有这个包含这个key： " + key + ", 无法赋值");
    }

    public void setId(Long id) {
        data.put("id", id);
    }

    public Object getId() {
        return data.get("id");
    }

    /**
     * 传进来一个空的map，用数据填充
     * 使用 key._relation.relate[0]这样的“XPath”保证key不重
     */
    public void getXPathData(String suffix, Map<String, String> valueMap) {
        String key;
        if (StringUtils.isBlank(suffix)) {
            key = metaCollection.getKey();
        } else {
            key = String.format("%s.%s", suffix, metaCollection.getKey());
        }

        for (Map.Entry<String, Object> stringObjectEntry : this.data.entrySet()) {
            String fieldKey = String.format("%s.%s", key, stringObjectEntry.getKey());
            valueMap.put(fieldKey, String.valueOf(stringObjectEntry.getValue()));
        }

        for (Map.Entry<String, List<FormEntity>> stringListEntry : this.relations.entrySet()) {
            String relationType = stringListEntry.getKey();

            List<FormEntity> value = stringListEntry.getValue();
            for (int i = 0; i < value.size(); i++) {
                FormEntity formEntity = value.get(i);
                String relationKey = String.format("%s._RELATION.%s[%d]", key, relationType, i);
                formEntity.getXPathData(relationKey, valueMap);
            }
        }
    }
}
