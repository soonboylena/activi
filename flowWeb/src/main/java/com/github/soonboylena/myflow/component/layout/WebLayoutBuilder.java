package com.github.soonboylena.myflow.component.layout;

import com.github.soonboylena.myflow.entity.config.ConfigureHolder;
import com.github.soonboylena.myflow.entity.core.MetaForm;
import com.github.soonboylena.myflow.vModel.uiComponent.Form;
import com.github.soonboylena.myflow.vModel.uiComponent.Page;
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
        Page page = new Page(metaForm.getCaption());
        return (Form) converterManager.convert(metaForm, page);
    }

//    public Page buildView(String viewKey) {
//
//        MetaView metaView = holder.getMetaView(viewKey);
//        return (Page) converterManager.convert(metaView);
//    }
}
