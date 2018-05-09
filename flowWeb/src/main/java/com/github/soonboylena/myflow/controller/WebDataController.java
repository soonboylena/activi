package com.github.soonboylena.myflow.controller;

import com.github.soonboylena.myflow.dynamic.service.WebValidService;
import com.github.soonboylena.myflow.dynamic.vModel.contant.ClientRouterMode;
import com.github.soonboylena.myflow.dynamic.vModel.uiAction.AbstractAction;
import com.github.soonboylena.myflow.dynamic.vModel.uiAction.LinkAction;
import com.github.soonboylena.myflow.dynamic.vModel.uiAction.MessageAction;
import com.github.soonboylena.myflow.entity.core.IEntity;
import com.github.soonboylena.myflow.dynamic.service.WebFormService;
import com.github.soonboylena.myflow.dynamic.support.UrlManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data")
public class WebDataController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WebFormService webFormSvs;

    @Autowired
    private WebValidService validService;

    // =============================数据提交================================================
    @PutMapping("/{formKey}")
    public AbstractAction pageSubmit(@PathVariable String formKey, @RequestBody Map<String, Map<String, Object>> map) {


        if (map != null) {

            IEntity entity = webFormSvs.form2Entity(formKey, map);
            // ============
            // 这个地方留定制校验
            validService.valid(entity);
            // =============
            Long id = webFormSvs.save(entity);
            LinkAction action = new LinkAction();
            action.setAlert("提交成功!");
            action.setMode(ClientRouterMode.replace);
            action.setUrl(UrlManager.pageInit(formKey, id));
            return action;
        }

        return MessageAction.message("没有接收到提交的数据！");
    }

    @GetMapping("/{formKey}/{id}")
    public Map data(@PathVariable("formKey") String formKey, @PathVariable("id") Long id) {
        return webFormSvs.findById(formKey, id);
    }

    // ===================================数据一览=============================================
    @GetMapping("list/{formKey}")
    public List<Map<String, Object>> list(@PathVariable("formKey") String formKey) {
        List<Map<String, Object>> all = webFormSvs.findAll(formKey);
        logger.info("{} 数据一览数目：{}", formKey, all.size());
        return all;
    }

    // ===================================画面选择下拉一览======================================
    @GetMapping("key/{formKey}/resources")
    public List<Map<String, Object>> listResources(@PathVariable("formKey") String formKey) {
        return webFormSvs.findResource(formKey);
    }

}
