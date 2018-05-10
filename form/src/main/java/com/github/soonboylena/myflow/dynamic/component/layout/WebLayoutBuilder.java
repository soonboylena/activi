package com.github.soonboylena.myflow.dynamic.component.layout;

import com.github.soonboylena.myflow.dynamic.vModel.UiContainer;
import com.github.soonboylena.myflow.entity.core.IMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class WebLayoutBuilder {

    @Autowired
    private ConverterManager converterManager;

    public UiContainer build(IMeta meta, UiContainer container) {
        return (UiContainer) converterManager.meta2Page(meta, container);
    }
}