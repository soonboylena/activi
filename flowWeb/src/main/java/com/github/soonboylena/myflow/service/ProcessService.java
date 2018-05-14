package com.github.soonboylena.myflow.service;

import com.github.soonboylena.myflow.Auth.util.SecurityUtil;
import com.github.soonboylena.myflow.dynamic.service.WebFormService;
import com.github.soonboylena.myflow.dynamic.service.WebLayoutService;
import com.github.soonboylena.myflow.dynamic.support.KeyConflictCollection;
import com.github.soonboylena.myflow.dynamic.support.UrlManager;
import com.github.soonboylena.myflow.dynamic.vModel.UiContainer;
import com.github.soonboylena.myflow.dynamic.vModel.uiComponent.Page;
import com.github.soonboylena.myflow.dynamic.vModel.uiComponent.UrlSection;
import com.github.soonboylena.myflow.entity.core.FormEntity;
import com.github.soonboylena.myflow.entity.core.IEntity;
import com.github.soonboylena.myflow.entity.core.MetaForm;
import com.github.soonboylena.myflow.workflow.utils.WorkFlowUtil;
import org.activiti.engine.*;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.form.FormEngine;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProcessService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessService.class);

    @Autowired
    private FormService formService;

    @Autowired
    private WebFormService webFormSvs;

    @Autowired
    private WebLayoutService webLayoutService;

    @Autowired
    private IdentityService identityService;

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
     *
     * @param processDefinition 流程
     */
    public Page generateLayout(ProcessDefinition processDefinition) {


        String processDefinitionId = processDefinition.getId();
        // 头节点
        Object renderedStartForm = formService.getRenderedStartForm(processDefinitionId);

        return validAndBuild(renderedStartForm);
    }

    private Page validAndBuild(Object form) {

        // 校验
        // 如果有问题 看看 MflFormEngine里边的逻辑
        if (!(form instanceof FormEntity)) {
            throw new RuntimeException("返回的结果不是IMeta。请检查是否activiti的流程文件里边，formKey的后缀是否是.mfl");
        }
        // 生成画面
        Page page = webLayoutService.buildFormLayout((FormEntity) form);
        return page;
    }


    /**
     * 生成布局： 任务
     * (参考)MflFormEngine
     *
     * @param taskId
     */
    public Page generateTaskLayout(String taskId) {
        Object renderedTaskForm = formService.getRenderedTaskForm(taskId);
        return validAndBuild(renderedTaskForm);
    }

    /**
     * 流程启动
     *
     * @param processDefinitionId
     * @param rawDataMap
     */
    @Transactional
    public void startProcess(String processDefinitionId, Map<String, Map<String, Object>> rawDataMap) {

        // 节点指定的formkey
        String startFormKey = formService.getStartFormKey(processDefinitionId);
        String formKey = WorkFlowUtil.noSuffixFormKey(startFormKey);
        logger.info("启动流程 processDefinitionId: {} ,formKey:{},", processDefinitionId, formKey);
        // 转成 IEntity接口
        IEntity entity = webFormSvs.form2Entity(formKey, rawDataMap);

        // ============
        // 这个地方留定制校验
//        validService.valid(entity);
        // =============
        Long businessKey = webFormSvs.save(entity);
        logger.debug(" - businessKey(数据id): {}", businessKey);

        ProcessInstance processInstance;
        try {
            identityService.setAuthenticatedUserId(SecurityUtil.currentUserId());
//            processInstance = runtimeService.startProcessInstanceByKey("leave", businessKey.toString(), Collections.emptyMap());

            processInstance = formService.submitStartFormData(processDefinitionId, WorkFlowUtil.formKeyMap(entity, businessKey));
            logger.info("流程 {} 已经启动。流程id：{}", processDefinitionId, processInstance.getId());

        } finally {
            identityService.setAuthenticatedUserId(null);
        }
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
