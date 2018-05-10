package com.github.soonboylena.myflow.dynamic.component.layout;

import com.github.soonboylena.myflow.dynamic.component.layout.converter.*;
import com.github.soonboylena.myflow.dynamic.vModel.UiContainer;
import com.github.soonboylena.myflow.dynamic.vModel.UiObject;
import com.github.soonboylena.myflow.entity.core.IEntity;
import com.github.soonboylena.myflow.entity.core.IMeta;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 维护一组converter。这些convert负责将底层基础实体与表示层的模型进行转换
 * 如果需要支持二次开发注入新的converter
 * 1 把converterList放到composite组件里边
 * 2 manager只跟composite打交道，并接受注入
 */
@Component
public class ConverterManager {

    private List<UIConverter> converterList = new ArrayList<>();

    @PostConstruct
    public void setDefaultConvert() {
        converterList.add(new FormConverter(this));
        converterList.add(new StringInputConverter());
        converterList.add(new SelectOneConverter());
        converterList.add(new ListConverter(this));
        converterList.add(new ResourceInputConverter());
        converterList.add(new TextInputConverter());
    }

    /**
     * meta ==> 画面定义
     *
     * @param metaItem
     * @return
     */
    public UiObject meta2Page(IMeta metaItem, UiContainer container, StatusStrategy statusStrategy) {

        for (UIConverter uiConverter : converterList) {
            if (uiConverter.support(metaItem)) {
                return uiConverter.meta2Page(metaItem, container, statusStrategy);
            }
        }
        return container;
    }

    public UiObject meta2Page(IMeta metaItem, UiContainer container) {

        for (UIConverter uiConverter : converterList) {
            if (uiConverter.support(metaItem)) {
                return uiConverter.meta2Page(metaItem, container);
            }
        }
        return container;
    }

    /**
     * 画面数据 ==> IEntity
     *
     * @param meta
     * @param data
     */
    public IEntity read(IMeta meta, Object data) {

        for (UIConverter uiConverter : converterList) {
            if (uiConverter.support(meta)) {
                return uiConverter.pageData2Entity(meta, data);
            }
        }
        return null;
    }

    public void entityData2PageMap(IEntity entity, Map collection) {
        for (UIConverter uiConverter : converterList) {
            if (uiConverter.support(entity.acquireMeta())) {
                uiConverter.entityData2PageMap(entity, collection);
                return;
            }
        }
    }
}
