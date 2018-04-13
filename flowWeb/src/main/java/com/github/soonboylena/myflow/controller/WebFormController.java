package com.github.soonboylena.myflow.controller;

import com.github.soonboylena.myflow.service.WebLayoutService;
import com.github.soonboylena.myflow.support.UrlManager;
import com.github.soonboylena.myflow.vModel.UiObject;
import com.github.soonboylena.myflow.vModel.uiAction.SubmitAction;
import com.github.soonboylena.myflow.vModel.uiComponent.Button;
import com.github.soonboylena.myflow.vModel.uiComponent.Page;
import com.github.soonboylena.myflow.vModel.uiComponent.UrlSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("page")
public class WebFormController {

    @Autowired
    private WebLayoutService webLayoutService;


    @GetMapping("init/{formKey}")
    public UrlSection init(@PathVariable("formKey") String formKey) {
        return new UrlSection(UrlManager.formLayout(formKey));
    }

    @GetMapping("layout/{formKey}")
    public UiObject layout(@PathVariable("formKey") String formKey) {

        Page page = new Page();
        webLayoutService.buildFormLayout(formKey, page);
        SubmitAction clientAction = new SubmitAction(UrlManager.submit(formKey));
        Button button = new Button("提交", clientAction);
//        page.addForm(form);

        page.addBtn(button);
        return page;
    }

    @GetMapping("data/{formKey}/{id}")
    public UiObject data(@PathVariable("formKey") String formKey, @PathVariable("id") String id) {
//        return webLayoutService.buildLayout(formKey);
        return null;
    }
}
