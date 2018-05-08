package com.github.soonboylena.myflow.controller;

import com.github.soonboylena.myflow.dynamic.vModel.UiObject;
import com.github.soonboylena.myflow.dynamic.vModel.contant.ButtonType;
import com.github.soonboylena.myflow.dynamic.vModel.uiAction.SubmitAction;
import com.github.soonboylena.myflow.dynamic.vModel.uiComponent.Button;
import com.github.soonboylena.myflow.dynamic.vModel.uiComponent.Page;
import com.github.soonboylena.myflow.dynamic.vModel.uiComponent.UrlSection;
import com.github.soonboylena.myflow.dynamic.service.WebLayoutService;
import com.github.soonboylena.myflow.dynamic.support.UrlManager;
import com.github.soonboylena.myflow.entity.core.IMeta;
import com.github.soonboylena.myflow.entity.core.MetaForm;
import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("process")
public class ProcessController {

    @Autowired
    private WebLayoutService webLayoutService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private FormService formService;


    @GetMapping("{processId}/init")
    public UrlSection init(@PathVariable("processId") String processId) {
        return new UrlSection(UrlManager.processLayout(processId));
    }

    @GetMapping("/{processId}/start/layout")
    public UiObject layout(@PathVariable("processId") String processId) {

        Page page = new Page();

        ProcessDefinition leave = repositoryService.createProcessDefinitionQuery().processDefinitionKey("leave").active().latestVersion().singleResult();

        Object renderedStartForm = formService.getRenderedStartForm(leave.getId());

        if (!(renderedStartForm instanceof MetaForm)) {
            throw new RuntimeException("返回的结果不是IMeta。请检查是否activiti的流程文件里边，formkey的后缀是否是.mfl");
        }

        webLayoutService.buildFormLayout((MetaForm) renderedStartForm, page);

        SubmitAction clientAction = new SubmitAction(UrlManager.processStart(processId));
        Button button = new Button("提交", ButtonType.primary, clientAction);

        page.addBtn(button);
        return page;
    }
}
