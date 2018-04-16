package com.github.soonboylena.myflow.service;

import com.github.soonboylena.myflow.component.layout.ConverterManager;
import com.github.soonboylena.myflow.entity.config.ConfigureHolder;
import com.github.soonboylena.myflow.entity.core.IEntity;
import com.github.soonboylena.myflow.entity.core.MetaForm;
import com.github.soonboylena.myflow.persistentneo4j.entity.DynamicEntity;
import com.github.soonboylena.myflow.persistentneo4j.service.DynamicFormQueryService;
import com.github.soonboylena.myflow.persistentneo4j.service.DynamicFormSaveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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


    /**
     * formData --> IEntity --> database
     *
     * @param key
     * @param map
     * @return
     */
    public Long save(String key, Map<String, Map<String, Object>> map) {

        if (map == null) {
            return null;
        }

        IEntity entity = saveForm(key, map);
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
    private IEntity saveForm(String formKey, Map<String, Map<String, Object>> rawData) {

        MetaForm metaForm = holder.getMetaForm(formKey);
        if (metaForm == null) {
            logger.error("formKey: {} 没有被定义在配置文件中。", formKey);
            throw new IllegalArgumentException("formKey： [" + formKey + "] 无法找到配置");
        }

        return converterManager.read(metaForm, rawData);
    }

    /**
     * database --> IEntity --> formData
     *
     * @param formKey
     * @param id
     * @return
     */
    public Map<String, Map<String, Object>> loadData(String formKey, Long id) {

        Map dataMap = new HashMap();

        MetaForm metaForm = holder.getMetaForm(formKey);
        Objects.requireNonNull(metaForm);

        IEntity entity = queryService.findById(metaForm, id);
        if (entity == null) return dataMap;
        converterManager.loadData(entity, dataMap);


        return dataMap;
    }
}
