package com.github.soonboylena.myflow.component.layout;

import com.github.soonboylena.myflow.entity.core.IMeta;
import com.github.soonboylena.myflow.vModel.UiContainer;
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
