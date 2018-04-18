package com.github.soonboylena.myflow.service;

import com.github.soonboylena.myflow.component.layout.ConverterManager;
import com.github.soonboylena.myflow.entity.config.ConfigureHolder;
import com.github.soonboylena.myflow.entity.core.IEntity;
import com.github.soonboylena.myflow.entity.core.ListEntity;
import com.github.soonboylena.myflow.entity.core.MetaForm;
import com.github.soonboylena.myflow.entity.core.MetaList;
import com.github.soonboylena.myflow.persistentneo4j.entity.DynamicEntity;
import com.github.soonboylena.myflow.persistentneo4j.service.DynamicFormQueryService;
import com.github.soonboylena.myflow.persistentneo4j.service.DynamicFormSaveService;
import com.github.soonboylena.myflow.support.KeyConflictCollection;
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
    private DynamicFormQueryService queryService;

    @Autowired
    private DynamicFormSaveService dynamicFormService;


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

        DynamicEntity saved = dynamicFormService.save(entity);
        return saved.getId();
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
    public Map findById(String formKey, Long id) {

        KeyConflictCollection dataMap = new KeyConflictCollection();

        MetaForm metaForm = holder.getMetaForm(formKey);
        Objects.requireNonNull(metaForm);

        IEntity entity = queryService.findById(metaForm, id);
        if (entity == null) return Collections.emptyMap();

        converterManager.entityData2PageMap(entity, dataMap);

        return dataMap.noConflictMap();
    }

    public List<Map<String, Object>> findAll(String formKey) {

        MetaForm metaForm = holder.getMetaForm(formKey);
        Objects.requireNonNull(metaForm);

        ListEntity byMeta = queryService.findByMeta(metaForm);
        KeyConflictCollection<Map<String, Object>> dataMap = new KeyConflictCollection<>();
        converterManager.entityData2PageMap(byMeta, dataMap);

        return dataMap.entryList();
    }
}
