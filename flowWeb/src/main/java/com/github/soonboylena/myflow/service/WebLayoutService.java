package com.github.soonboylena.myflow.service;

import com.github.soonboylena.myflow.component.layout.WebLayoutBuilder;
import com.github.soonboylena.myflow.vModel.uiComponent.Form;
import com.github.soonboylena.myflow.vModel.uiComponent.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebLayoutService {

    @Autowired
    private WebLayoutBuilder layoutBuilder;

    public Form buildViewLayout(String viewKey) {
        //TODO
//        return layoutBuilder.buildForm(formKey);
        return null;
    }

    public Form buildFormLayout(String formKey) {
        return layoutBuilder.buildForm(formKey);
    }
}
