package com.github.soonboylena.activiti.controller;

import com.github.soonboylena.activiti.service.WebFormService;
import com.github.soonboylena.activiti.service.WebLayoutService;
import com.github.soonboylena.activiti.vModel.UiObject;
import com.github.soonboylena.activiti.vModel.uiAction.ClientAction;
import com.github.soonboylena.entity.core.IEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("page")
public class WebLayoutController {

    @Autowired
    private WebLayoutService webLayoutService;

    @Autowired
    private WebFormService formSvc;


    @GetMapping("layout/{formKey}")
    public UiObject layout(@PathVariable("formKey") String formKey) {
        return webLayoutService.buildLayout(formKey);
    }

    @GetMapping("data/{formKey}/{id}")
    public UiObject data(@PathVariable("formKey") String formKey, @PathVariable("id") String id) {
        return webLayoutService.buildLayout(formKey);
    }

    @PutMapping("data/{formKey}")
    public ClientAction createData(@PathVariable("formKey") String formKey, @RequestBody Map<String, Object> map) {

        IEntity iEntity = formSvc.cleanUp(formKey, map);

        return ClientAction.successMessageAction("Ok");
    }
}
