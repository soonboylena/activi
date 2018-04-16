package com.github.soonboylena.myflow.component.layout;

import com.github.soonboylena.myflow.component.layout.converter.*;
import com.github.soonboylena.myflow.entity.core.MetaForm;
import com.github.soonboylena.myflow.vModel.UiContainer;
import com.github.soonboylena.myflow.vModel.UiObject;
import com.github.soonboylena.myflow.entity.core.IEntity;
import com.github.soonboylena.myflow.entity.core.IMeta;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 维护一组converter。这些convert负责将底层基础实体与表示层的模型进行转换
 */
@Component
public class ConverterManager {

    private List<UIConverter> converterList = new ArrayList<>();

    @PostConstruct
    public void setDefaultConvert() {
        converterList.add(new FormConverter(this));
        converterList.add(new StringInputConverter());
        converterList.add(new SelectOneConverter());
        converterList.add(new ListConverter());
    }

    /**
     * meta ==> 画面定义
     *
     * @param metaItem
     * @return
     */
    public UiObject convert(IMeta metaItem, UiContainer container) {

        for (UIConverter uiConverter : converterList) {
            if (uiConverter.support(metaItem)) {
                return uiConverter.convert(metaItem, container);
            }
        }
        return null;
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
                return uiConverter.read(meta, data);
            }
        }
        return null;
    }

    public void loadData(IEntity entity, Map topMap) {
        for (UIConverter uiConverter : converterList) {
            if (uiConverter.support(entity.acquireMeta())) {
                uiConverter.loadData(entity, topMap);
                return;
            }
        }
    }
}
