package com.github.soonboylena.myflow.controller;

import com.github.soonboylena.myflow.entity.core.IEntity;
import com.github.soonboylena.myflow.service.WebFormService;
import com.github.soonboylena.myflow.service.WebValidService;
import com.github.soonboylena.myflow.support.UrlManager;
import com.github.soonboylena.myflow.vModel.contant.ClientRouterMode;
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
    private WebValidService validService;

    @PutMapping("/{formKey}")
    public AbstractAction pageSubmit(@PathVariable String formKey, @RequestBody Map<String, Map<String, Object>> map) {

        if (map != null) {

            IEntity entity = webFromSvs.form2Entity(formKey, map);
            // ============
            // 这个地方留定制校验
            validService.valid(entity);
            // =============
            Long id = webFromSvs.save(entity);
            LinkAction action = new LinkAction();
            action.setAlert("提交成功!");
            action.setMode(ClientRouterMode.replace);
            action.setUrl(UrlManager.pageInit(formKey, id));
            return action;
        }

        return MessageAction.error("没有接收到提交的数据！");
    }

    @GetMapping("/{formKey}/{id}")
    public Map data(@PathVariable("formKey") String formKey, @PathVariable("id") Long id) {

        return webFromSvs.loadData(formKey, id);
    }

}
