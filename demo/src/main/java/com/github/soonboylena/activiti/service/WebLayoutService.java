package com.github.soonboylena.activiti.service;

import com.github.soonboylena.activiti.component.layout.WebLayoutBuilder;
import com.github.soonboylena.activiti.vModel.uiComponent.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebLayoutService {

    @Autowired
    private WebLayoutBuilder layoutBuilder;

    public Section buildLayout(String formKey) {
        return layoutBuilder.buildForm(formKey);
    }
}
