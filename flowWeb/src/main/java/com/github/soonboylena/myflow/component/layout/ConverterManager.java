package com.github.soonboylena.myflow.component.layout;

import com.github.soonboylena.myflow.component.layout.converter.*;
import com.github.soonboylena.myflow.vModel.UiObject;
import com.github.soonboylena.myflow.entity.core.IEntity;
import com.github.soonboylena.myflow.entity.core.IMeta;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 维护一组converter。这些convert负责将底层基础实体与表示层的模型进行转换
 */
@Component
public class ConverterManager {

    private List<UIConverter> converterList = new ArrayList<>();

    @PostConstruct
    public void setDefaultConvert() {
        converterList.add(new FormConverter(this));
//        converterList.add(new ViewConverter(this));
        converterList.add(new StringInputConverter());
        converterList.add(new SelectOneConverter());
    }

    /**
     * meta ==> 画面
     *
     * @param metaItem
     * @return
     */
    public UiObject convert(IMeta metaItem) {

        for (UIConverter uiConverter : converterList) {
            if (uiConverter.support(metaItem)) {
                return uiConverter.convert(metaItem);
            }
        }
        return null;
    }

    /**
     * 画面数据 ==> data
     *
     * @param meta
     * @param map
     */
    public IEntity read(IMeta meta, Object data) {

        for (UIConverter uiConverter : converterList) {
            if (uiConverter.support(meta)) {
                return uiConverter.read(meta, data);
            }
        }
        return null;
    }

}
