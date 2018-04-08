package com.github.soonboylena.myflow.controller;

import com.github.soonboylena.myflow.entity.core.IEntity;
import com.github.soonboylena.myflow.persistentneo4j.entity.DynamicEntity;
import com.github.soonboylena.myflow.persistentneo4j.service.FormService;
import com.github.soonboylena.myflow.service.WebFormService;
import com.github.soonboylena.myflow.service.WebLayoutService;
import com.github.soonboylena.myflow.support.UrlManager;
import com.github.soonboylena.myflow.vModel.UiObject;
import com.github.soonboylena.myflow.vModel.uiAction.AbstractAction;
import com.github.soonboylena.myflow.vModel.uiAction.LinkAction;
import com.github.soonboylena.myflow.vModel.uiAction.SubmitAction;
import com.github.soonboylena.myflow.vModel.uiComponent.Button;
import com.github.soonboylena.myflow.vModel.uiComponent.Form;
import com.github.soonboylena.myflow.vModel.uiComponent.Page;
import com.github.soonboylena.myflow.vModel.uiComponent.UrlSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("page")
public class WebFormController {

    @Autowired
    private WebLayoutService webLayoutService;

    @Autowired
    private WebFormService webFromSvs;

    @Autowired
    private FormService formService;

    @GetMapping("init/f-{formKey}")
    public UrlSection init(@PathVariable("formKey") String formKey) {
        return new UrlSection(UrlManager.formLayout(formKey));
    }

    @GetMapping("layout/f-{formKey}")
    public UiObject layout(@PathVariable("formKey") String formKey) {

        Form form = webLayoutService.buildFormLayout(formKey);

        Page page = new Page(form.getCaption());
        SubmitAction clientAction = new SubmitAction(UrlManager.submit(formKey), formKey);
        Button button = new Button("提交", clientAction);
        page.addForm(form);

        page.addBtn(button);
        return page;
    }

    @GetMapping("data/f-{formKey}/{id}")
    public UiObject data(@PathVariable("formKey") String formKey, @PathVariable("id") String id) {
//        return webLayoutService.buildLayout(formKey);
        return null;
    }

    @PutMapping("data/f-{formKey}")
    public AbstractAction pageSubmit(@PathVariable String formKey, @RequestBody Map<String, Map<String, Object>> map) {

        if (map != null) {

            if (!map.containsKey(formKey)) {
                throw new IllegalStateException("单form提交，但是提交的数据中不包含 formKey: [" + formKey + "] 指定的form");
            }

            IEntity iEntity = webFromSvs.cleanUp(formKey, map.get(formKey));

            DynamicEntity save = formService.save(iEntity);

            LinkAction action = new LinkAction();
            action.setAlert("提交成功!");
            action.setUrl(UrlManager.formInit(formKey, save.getId()));
            return action;
        }


        return null;
    }
}
