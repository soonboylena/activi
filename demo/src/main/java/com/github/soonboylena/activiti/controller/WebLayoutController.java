package com.github.soonboylena.activiti.controller;

import com.github.soonboylena.activiti.service.WebLayoutService;
import com.github.soonboylena.activiti.vModel.UiObject;
import com.github.soonboylena.activiti.vModel.uiComponent.Section;
import com.github.soonboylena.activiti.vModel.uiComponent.UrlSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("layout")
public class WebLayoutController {

    @Autowired
    private WebLayoutService webLayoutService;

    @GetMapping("{formKey}")
    public UiObject layout(@PathVariable("formKey") String formKey) {
        return webLayoutService.buildLayout(formKey);
    }

}
