package com.github.soonboylena.activiti.component.layout;

import com.github.soonboylena.activiti.component.layout.converter.FormConverter;
import com.github.soonboylena.activiti.component.layout.converter.StringInputConverter;
import com.github.soonboylena.activiti.component.layout.converter.UIConverter;
import com.github.soonboylena.activiti.vModel.UiObject;
import com.github.soonboylena.entity.core.IMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConverterManager {

    private List<UIConverter> converterList = new ArrayList<>();

    @Autowired
    private FormConverter formConverter;


    @PostConstruct
    public void setDefaultConvert() {
        converterList.add(formConverter);
        converterList.add(new StringInputConverter());
    }

    public UiObject convert(IMeta metaItem) {

        for (UIConverter uiConverter : converterList) {
            if (uiConverter.support(metaItem)) {
                return uiConverter.convert(metaItem);
            }
        }
        return null;
    }
}
