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
import com.github.soonboylena.myflow.service.ProcessWebService;
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
    private ProcessWebService processWebService;

    @GetMapping("{processDefinitionKey}/init")
    public UrlSection init(@PathVariable("processDefinitionKey") String processDefinitionKey) {
        return processWebService.initProcess(processDefinitionKey);
    }

    /**
     * 打开流程画面
     *
     * @param processDefinitionKey
     * @return
     */
    @GetMapping("/{processDefinitionKey}/start/layout")
    public UiObject layout(@PathVariable("processDefinitionKey") String processDefinitionKey) {

        ProcessDefinition processDefinition = processWebService.latestProcessDefinition(processDefinitionKey);
        Page page = processWebService.generateLayout(processDefinition);
        String processDefinitionId = processDefinition.getId();
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
    public MessageAction startProcess(@PathVariable("processDefinitionId") String processDefinitionId, @RequestBody Map<String, Map<String, String>> rawDataMap) {
        processWebService.startProcess(processDefinitionId, rawDataMap);
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
        List<Map<String, Object>> tasks = processWebService.myTask(s);
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
        boolean claim = processWebService.claim(taskId, SecurityUtil.currentUserName());
        return claim;
    }

    /**
     * 任务处理画面
     */
    @GetMapping("/task/{taskId}/handle/layout")
    public UiObject handleLayout(@PathVariable("taskId") String taskId) {
        return processWebService.generateTaskLayout(taskId);
    }

}
