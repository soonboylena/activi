package com.github.soonboylena.myflow.service;

import com.github.soonboylena.myflow.Auth.util.SecurityUtil;
import com.github.soonboylena.myflow.dynamic.service.WebFormService;
import com.github.soonboylena.myflow.dynamic.service.WebLayoutService;
import com.github.soonboylena.myflow.dynamic.vModel.uiComponent.Page;
import com.github.soonboylena.myflow.entity.core.IEntity;
import com.github.soonboylena.myflow.entity.core.MetaForm;
import com.github.soonboylena.myflow.workflow.utils.WorkFlowUtil;
import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Map;
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
    private RuntimeService runtimeService;

    /**
     * 生成布局
     *
     * @param processDefinitionId 流程id
     * @param page                容器
     */
    public void generateLayout(String processDefinitionId, Page page) {
        // 头节点
        Object renderedStartForm = formService.getRenderedStartForm(processDefinitionId);

        // 校验
        // 如果有问题 看看 MflFormEngine里边的逻辑
        if (!(renderedStartForm instanceof MetaForm)) {
            throw new RuntimeException("返回的结果不是IMeta。请检查是否activiti的流程文件里边，formKey的后缀是否是.mfl");
        }
        // 生成画面
        webLayoutService.buildFormLayout((MetaForm) renderedStartForm, page);
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
            processInstance = runtimeService.startProcessInstanceByKey("leave", businessKey.toString(), Collections.emptyMap());
            logger.info("流程 {} 已经启动。流程id：{}", processDefinitionId, processInstance.getId());
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
    }

}
