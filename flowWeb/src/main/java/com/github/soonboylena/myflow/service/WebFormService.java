package com.github.soonboylena.myflow.service;

import com.github.soonboylena.myflow.component.layout.ConverterManager;
import com.github.soonboylena.myflow.entity.config.ConfigureHolder;
import com.github.soonboylena.myflow.entity.core.IEntity;
import com.github.soonboylena.myflow.entity.core.MetaForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * 处理提交上来的数据
 */
@Service
public class WebFormService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConfigureHolder holder;

    @Autowired
    private ConverterManager converterManager;

    public IEntity cleanUp(String key, Map<String, Map<String, Object>> map) {

        if (map == null) {
            return null;
        }

        return cleanUpForm(key, map.get(key));

//        logger.info("按照view进行提交。viewKey:{}", key);
//        return cleanUpView(key, map);
    }


//    /**
//     * 按照view对提交的数据处理
//     *
//     * @param key
//     * @param map
//     * @return
//     */
//    private IEntity cleanUpView(String key, Map<String, Map<String, Object>> map) {
//        MetaView metaView = holder.getMetaView(key);
//        if (metaView == null) {
//            logger.error("metaView: {} 没有被定义在配置文件中。", key);
//            throw new IllegalArgumentException("viewKey [" + key + "] 无法找到配置");
//        }
//
//        return converterManager.read(metaView, map);
//    }

    /**
     * 按照form对提交的数据处理
     *
     * @param formKey
     * @param map
     * @return
     */
    private IEntity cleanUpForm(String formKey, Map<String, Object> map) {

        MetaForm metaForm = holder.getMetaForm(formKey);
        if (metaForm == null) {
            logger.error("formKey: {} 没有被定义在配置文件中。", formKey);
            throw new IllegalArgumentException("formKey： [" + formKey + "] 无法找到配置");
        }

        return converterManager.read(metaForm, map);
    }
}
