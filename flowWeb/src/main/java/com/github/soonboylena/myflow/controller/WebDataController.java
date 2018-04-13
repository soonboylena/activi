package com.github.soonboylena.myflow.controller;

import com.github.soonboylena.myflow.entity.core.IEntity;
import com.github.soonboylena.myflow.persistentneo4j.entity.DynamicEntity;
import com.github.soonboylena.myflow.persistentneo4j.service.DynamicFormService;
import com.github.soonboylena.myflow.service.WebFormService;
import com.github.soonboylena.myflow.support.UrlManager;
import com.github.soonboylena.myflow.vModel.uiAction.AbstractAction;
import com.github.soonboylena.myflow.vModel.uiAction.LinkAction;
import com.github.soonboylena.myflow.vModel.uiAction.MessageAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/data")
public class WebDataController {

    @Autowired
    private WebFormService webFromSvs;

    @Autowired
    private DynamicFormService dynamicFormService;

    @PutMapping("/{formKey}")
    public AbstractAction pageSubmit(@PathVariable String formKey, @RequestBody Map<String, Map<String, Object>> map) {

        if (map != null) {

            IEntity iEntity = webFromSvs.cleanUp(formKey, map);
            DynamicEntity save = dynamicFormService.save(iEntity);

            LinkAction action = new LinkAction();
            action.setAlert("提交成功!");
            action.setUrl(UrlManager.pageInit(formKey, save.getId()));
            return action;
        }

        return MessageAction.error("没有接收到提交的数据！");
    }

}
