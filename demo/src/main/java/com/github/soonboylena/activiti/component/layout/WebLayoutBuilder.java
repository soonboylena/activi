package com.github.soonboylena.activiti.component.layout;

import com.github.soonboylena.activiti.vModel.UiObject;
import com.github.soonboylena.activiti.vModel.uiComponent.Form;
import com.github.soonboylena.activiti.vModel.uiComponent.Section;
import com.github.soonboylena.entity.config.ConfigureHolder;
import com.github.soonboylena.entity.core.MetaForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebLayoutBuilder {

    @Autowired
    private ConfigureHolder holder;

    @Autowired
    private ConverterManager converterManager;

    public Form buildForm(String formKey) {

        MetaForm metaForm = holder.getMetaForm(formKey);
        return (Form) converterManager.convert(metaForm);
    }
}
