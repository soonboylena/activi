package com.github.soonboylena.myflow.controller;

import com.github.soonboylena.myflow.service.WebLayoutService;
import com.github.soonboylena.myflow.vModel.UiContainer;
import com.github.soonboylena.myflow.vModel.UiObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("list")
public class WebListController {

    @Autowired
    private WebLayoutService layoutSvs;

    @GetMapping("layout/{formKey}")
    public UiObject list(@PathVariable("formKey") String formKey) {

        UiContainer uiContainer = layoutSvs.listLayout(formKey);
        return uiContainer;
    }
}
