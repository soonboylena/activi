package com.github.soonboylena.myflow.controller;

import com.github.soonboylena.myflow.dynamic.support.UrlManager;
import com.github.soonboylena.myflow.dynamic.vModel.UiObject;
import com.github.soonboylena.myflow.dynamic.vModel.contant.ButtonType;
import com.github.soonboylena.myflow.dynamic.vModel.uiAction.MessageAction;
import com.github.soonboylena.myflow.dynamic.vModel.uiAction.SubmitAction;
import com.github.soonboylena.myflow.dynamic.vModel.uiComponent.Button;
import com.github.soonboylena.myflow.dynamic.vModel.uiComponent.Page;
import com.github.soonboylena.myflow.dynamic.vModel.uiComponent.UrlSection;
import com.github.soonboylena.myflow.service.ProcessService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("process")
public class ProcessController {


    @Autowired
    private RepositoryService repositoryService;


    @Autowired
    private ProcessService processService;

    @GetMapping("{processDefinitionKey}/init")
    public UrlSection init(@PathVariable("processDefinitionKey") String processDefinitionKey) {
        // 取最新流程
        ProcessDefinition leave = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .active()
                .latestVersion()
                .singleResult();
        String processDefinitionId = leave.getId();
        return new UrlSection(UrlManager.processLayout(processDefinitionId));
    }

    @GetMapping("/{processDefinitionId}/start/layout")
    public UiObject layout(@PathVariable("processDefinitionId") String processDefinitionId) {

        Page page = new Page();

        processService.generateLayout(processDefinitionId, page);

        // 按钮
        SubmitAction clientAction = new SubmitAction(UrlManager.processStart(processDefinitionId));
        Button button = new Button("提交", ButtonType.primary, clientAction);

        page.addBtn(button);
        return page;
    }

    @PostMapping("/{processDefinitionId}/start")
    public MessageAction startProcess(@PathVariable("processDefinitionId") String processDefinitionId, @RequestBody Map<String, Map<String, Object>> rawDataMap) {
        processService.startProcess(processDefinitionId, rawDataMap);
        return MessageAction.message("办理完了");
    }
}
