package com.github.soonboylena.myflow.service;

import com.github.soonboylena.myflow.Auth.util.SecurityUtil;
import com.github.soonboylena.myflow.dynamic.service.WebFormService;
import com.github.soonboylena.myflow.dynamic.service.WebLayoutService;
import com.github.soonboylena.myflow.dynamic.support.KeyConflictCollection;
import com.github.soonboylena.myflow.dynamic.support.UrlManager;
import com.github.soonboylena.myflow.dynamic.vModel.UiContainer;
import com.github.soonboylena.myflow.dynamic.vModel.uiComponent.Page;
import com.github.soonboylena.myflow.dynamic.vModel.uiComponent.UrlSection;
import com.github.soonboylena.myflow.entity.core.MetaForm;
import com.github.soonboylena.myflow.workflow.service.DynamicFormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProcessWebService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessWebService.class);

    @Autowired
    private DynamicFormService dynamicFormService;

    @Autowired
    private WebFormService webFormSvs;

    @Autowired
    private WebLayoutService webLayoutService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;


    public ProcessDefinition latestProcessDefinition(String processDefinitionKey) {
        // 取最新流程
        return repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .active()
                .latestVersion()
                .singleResult();
    }

    /**
     * 打开启动新流程的画面
     *
     * @param processDefinitionKey
     * @return
     */
    public UrlSection initProcess(String processDefinitionKey) {
        return new UrlSection(UrlManager.processLayout(processDefinitionKey));
    }

    /**
     * 生成布局
     * 流程头结点，不考虑数据、状态
     *
     * @param processDefinition 流程
     */
    public Page generateLayout(ProcessDefinition processDefinition) {
        String processDefinitionId = processDefinition.getId();
        String name = processDefinition.getName();
        int version = processDefinition.getVersion();

        Page page = new Page();
        page.setSubTitle(String.format("%s:%s:%s", name, version, processDefinitionId));

        MetaForm metaForm = dynamicFormService.startProcess(processDefinition);
        webLayoutService.buildFormLayout(metaForm, page);
        return page;
    }

//    private Page validAndBuild(Object form) {
//
//        // 校验
//        // 如果有问题 看看 MflFormEngine里边的逻辑
//        if (!(form instanceof FormEntity)) {
//            throw new RuntimeException("返回的结果不是IMeta。请检查是否activiti的流程文件里边，formKey的后缀是否是.mfl");
//        }
//        // 生成画面
//        return webLayoutService.buildFormLayout((FormEntity) form);
//    }


    /**
     * 生成布局： 任务
     * (参考)MflFormEngine
     * 过程结点，考虑数据以及状态
     *
     * @param taskId
     */
    public Page generateTaskLayout(String taskId) {

//        Object renderedTaskForm = formService.getRenderedTaskForm(taskId);
//        return validAndBuild(renderedTaskForm);
        return null;
    }

    /**
     * 流程启动
     *
     * @param processDefinitionId
     * @param rawDataMap
     */
    public void startProcess(String processDefinitionId, Map<String, Map<String, String>> rawDataMap) {

        String userName = SecurityUtil.currentUserName();

        KeyConflictCollection<Map<String, String>> keyConflictCollection = webFormSvs.putIntoCollection(rawDataMap);
        ProcessInstance processInstance = dynamicFormService.submitStartForm(processDefinitionId, userName, keyConflictCollection);
    }

    /**
     * 根据用户名取待办列表
     *
     * @param userName
     * @return
     */
    public List<Map<String, Object>> myTask(String userName) {
        List<Task> list = taskService.createTaskQuery().taskCandidateOrAssigned(userName).orderByTaskId().desc().list();
        return list.stream().map(this::task2Map).collect(Collectors.toList());
    }

    /**
     * 签收任务
     */

    public boolean claim(String taskId, String userName) {
        taskService.claim(taskId, userName);
        logger.debug("task:{} 已经被{}签收. ", taskId, userName);
        return true;
    }

    /**
     * 任务列表转map
     *
     * @param task
     * @return
     */
    private Map<String, Object> task2Map(Task task) {
        Map<String, Object> map = new HashMap<>();
        map.put("taskId", task.getId());
        map.put("taskName", task.getName());
        map.put("createDate", task.getCreateTime().getTime());
        map.put("owner", task.getOwner());
        map.put("claimed", task.getAssignee() != null);
        return map;
    }

}
