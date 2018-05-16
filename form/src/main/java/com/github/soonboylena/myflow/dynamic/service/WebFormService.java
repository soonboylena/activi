package com.github.soonboylena.myflow.dynamic.service;

import com.github.soonboylena.myflow.dynamic.component.layout.ConverterManager;
import com.github.soonboylena.myflow.entity.config.ConfigureHolder;
import com.github.soonboylena.myflow.entity.core.*;
import com.github.soonboylena.myflow.dynamic.support.KeyConflictCollection;
import com.github.soonboylena.myflow.framework.web.FormQueryService;
import com.github.soonboylena.myflow.framework.web.FormSaveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 处理数据
 */
@Service
public class WebFormService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConfigureHolder holder;

    @Autowired
    private ConverterManager converterManager;

    @Autowired
    private FormQueryService queryService;

    @Autowired
    private FormSaveService dynamicFormService;


    private KeyConflictCollection<Map<String, Object>> putIntoCollection(Map<String, Map<String, Object>> rawMap) {

        KeyConflictCollection<Map<String, Object>> keyConflictCollection = new KeyConflictCollection<>();
        for (Map.Entry<String, Map<String, Object>> stringMapEntry : rawMap.entrySet()) {
            String key = stringMapEntry.getKey();
            Map<String, Object> value = stringMapEntry.getValue();
            keyConflictCollection.putByIndexedKey(key, value);
        }
        return keyConflictCollection;
    }


    /**
     * formData --> IEntity --> database
     *
     * @param entity
     * @return
     */
    public Long save(IEntity entity) {
        return dynamicFormService.save(entity);
    }

    /**
     * 按照form对提交的数据处理
     *
     * @param formKey
     * @param
     * @return
     */
    public IEntity form2Entity(String formKey, Map<String, Map<String, Object>> rawData) {

        MetaForm metaForm = holder.getMetaForm(formKey);
        if (metaForm == null) {
            logger.error("formKey: {} 没有被定义在配置文件中。", formKey);
            throw new IllegalArgumentException("formKey： [" + formKey + "] 无法找到配置");
        }

        KeyConflictCollection<Map<String, Object>> keyConflictCollection = putIntoCollection(rawData);
        return converterManager.read(metaForm, keyConflictCollection);
    }

    /**
     * database --> IEntity --> formData
     *
     * @param formKey
     * @param id
     * @return
     */
    public Map<String, Object> findById(String formKey, Long id) {


        MetaForm metaForm = holder.getMetaForm(formKey);
        Objects.requireNonNull(metaForm);

        IEntity entity = queryService.findById(metaForm, id);
        if (entity == null) return Collections.emptyMap();

        Map<String, Object> data = converterManager.entityData2PageMap(entity);

        return data;
    }

    /**
     * 查找指定formKey的所有数据
     *
     * @param formKey
     * @return
     */
    public List<Map<String, Object>> findAll(String formKey) {

        MetaForm metaForm = holder.getMetaForm(formKey);
        Objects.requireNonNull(metaForm);

        ListEntity byMeta = queryService.findByMeta(metaForm);
//        KeyConflictCollection<Map<String, Object>> dataMap = new KeyConflictCollection<>();
        Map<String, Object> stringObjectMap = converterManager.entityData2PageMap(byMeta);

        return (List<Map<String, Object>>) stringObjectMap.get(metaForm.getKey());
    }

    /**
     * 返回作为下拉列表框的内容；(选择一个资源）
     *
     * @param formKey
     * @return
     */
    public List<Map<String, Object>> findResource(String formKey) {


        MetaForm metaForm = holder.getMetaForm(formKey);
        Objects.requireNonNull(metaForm);

        List<Map<String, Object>> all = this.findAll(formKey);

        String businessKey = metaForm.getBusinessKey();

        List<Map<String, Object>> rtnList = new ArrayList<>();

        for (Map<String, Object> stringObjectMap : all) {

            Object id = stringObjectMap.get("id");
            Object value = stringObjectMap.get(businessKey);
            Object option = String.valueOf(id) + "/" + value;

            Map<String, Object> rtnMap = new HashMap<>();
            rtnMap.put("id", id);
            rtnMap.put("value", value);
            rtnMap.put("option", option);
            rtnList.add(rtnMap);
        }

        return rtnList;
    }
}
