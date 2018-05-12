package com.github.soonboylena.myflow.controller;

import com.github.soonboylena.myflow.Auth.util.SecurityUtil;
import com.github.soonboylena.myflow.dynamic.support.UrlManager;
import com.github.soonboylena.myflow.dynamic.vModel.UiObject;
import com.github.soonboylena.myflow.dynamic.vModel.contant.ButtonType;
import com.github.soonboylena.myflow.dynamic.vModel.uiAction.MessageAction;
import com.github.soonboylena.myflow.dynamic.vModel.uiAction.SubmitAction;
import com.github.soonboylena.myflow.dynamic.vModel.uiComponent.Button;
import com.github.soonboylena.myflow.dynamic.vModel.uiComponent.Page;
import com.github.soonboylena.myflow.dynamic.vModel.uiComponent.UrlSection;
import com.github.soonboylena.myflow.service.ProcessService;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("process")
public class ProcessController {

    private static final Logger logger = LoggerFactory.getLogger(ProcessController.class);

    @Autowired
    private ProcessService processService;

    @GetMapping("{processDefinitionKey}/init")
    public UrlSection init(@PathVariable("processDefinitionKey") String processDefinitionKey) {
        return processService.initProcess(processDefinitionKey);
    }

    @GetMapping("/{processDefinitionKey}/start/layout")
    public UiObject layout(@PathVariable("processDefinitionKey") String processDefinitionKey) {

        Page page = new Page();

        ProcessDefinition processDefinition = processService.latestProcessDefinition(processDefinitionKey);
        processService.generateLayout(processDefinition, page);

        String processDefinitionId = processDefinition.getId();
        String name = processDefinition.getName();
        int version = processDefinition.getVersion();

        page.setSubTitle(String.format("%s:%s", name, version));

        // 按钮
        SubmitAction clientAction = new SubmitAction(UrlManager.processStart(processDefinitionId));
        Button button = new Button("提交", ButtonType.primary, clientAction);

        page.addBtn(button);
        return page;
    }

    /**
     * 流程启动
     *
     * @param processDefinitionId
     * @param rawDataMap
     * @return
     */
    @PostMapping("/{processDefinitionId}/start")
    public MessageAction startProcess(@PathVariable("processDefinitionId") String processDefinitionId, @RequestBody Map<String, Map<String, Object>> rawDataMap) {
        processService.startProcess(processDefinitionId, rawDataMap);
        return MessageAction.message("办理完了");
    }

    /**
     * 任务一览
     *
     * @return
     */
    @GetMapping("myTask")
    public List<Map<String, Object>> myProcess() {
        String s = SecurityUtil.currentUserName();
        List<Map<String, Object>> tasks = processService.myTask(s);
        logger.debug("用户 {} 取待办 {} 条", s, tasks.size());
        return tasks;
    }

    /**
     * 任务签收
     *
     * @param taskId
     * @return
     */
    @GetMapping("task/{taskId}/claim")
    public boolean claim(@PathVariable("taskId") String taskId) {
        boolean claim = processService.claim(taskId, SecurityUtil.currentUserName());
        return claim;
    }

    /**
     * 任务处理画面
     */
    @GetMapping("/task/{taskId}/handle/layout")
    public UiObject handleLayout(@PathVariable("taskId") String taskId) {
        Page page = new Page();
        processService.generateTaskLayout(taskId, page);
        return page;
    }

}
