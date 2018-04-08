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
import com.github.soonboylena.myflow.vModel.uiAction.MessageAction;
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
public class WebLayoutController {

    @Autowired
    private WebLayoutService webLayoutService;

    @Autowired
    private WebFormService webFromSvs;

    @Autowired
    private FormService formService;

    @GetMapping("init/{formKey}")
    public UrlSection init(@PathVariable("formKey") String formKey) {

        return new UrlSection(UrlManager.layout(formKey));
    }

    @GetMapping("layout/{formKey}")
    public UiObject layout(@PathVariable("formKey") String formKey) {

        Form form = webLayoutService.buildLayout(formKey);

        Page page = new Page(form.getCaption());
        SubmitAction clientAction = new SubmitAction(UrlManager.submit(formKey), formKey);
        Button button = new Button("提交", clientAction);
        page.addForm(form);

        page.addBtn(button);
        return page;
    }

    @GetMapping("data/{formKey}/{id}")
    public UiObject data(@PathVariable("formKey") String formKey, @PathVariable("id") String id) {
        return webLayoutService.buildLayout(formKey);
    }

    @PutMapping("data/{formKey}")
    public AbstractAction pageSubmit(@PathVariable String formKey, @RequestBody Map<String, Map<String, Object>> map) {

        if (map != null) {

            if (map.size() != 1) {
                return MessageAction.error("现在还不支持多个form提交");
            }
            List<IEntity> entityList = new ArrayList<>(map.size());
            for (String s : map.keySet()) {
                IEntity iEntity = webFromSvs.cleanUp(s, map.get(s));
                entityList.add(iEntity);
            }

            Iterable<DynamicEntity> save = formService.save(entityList);

            List<Long> ids = new ArrayList<>();
            save.forEach(s -> ids.add(s.getId()));
            Long aLong = ids.get(0);

            LinkAction action = new LinkAction();
            action.setAlert("提交成功!");
            action.setUrl(UrlManager.pageInit(formKey, aLong));
            return action;
        }


        return null;
    }
}
