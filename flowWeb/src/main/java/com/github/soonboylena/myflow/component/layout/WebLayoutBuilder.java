package com.github.soonboylena.myflow.component.layout;

import com.github.soonboylena.myflow.entity.config.ConfigureHolder;
import com.github.soonboylena.myflow.entity.core.MetaForm;
import com.github.soonboylena.myflow.vModel.UiContainer;
import com.github.soonboylena.myflow.vModel.uiComponent.Form;
import com.github.soonboylena.myflow.vModel.uiComponent.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class WebLayoutBuilder {

    @Autowired
    private ConfigureHolder holder;

    @Autowired
    private ConverterManager converterManager;

    public UiContainer buildForm(String formKey, UiContainer container) {

        MetaForm metaForm = holder.getMetaForm(formKey);
        Objects.requireNonNull(metaForm, "没有找到formKey: [ " + formKey + " ] 的定义.");

        container.setCaption(metaForm.getCaption());
        return (UiContainer) converterManager.convert(metaForm, container);
    }

}
