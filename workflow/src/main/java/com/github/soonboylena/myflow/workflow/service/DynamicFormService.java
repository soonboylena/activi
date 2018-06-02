package com.github.soonboylena.myflow.workflow.service;

import com.github.soonboylena.myflow.dynamic.support.KeyConflictCollection;
import com.github.soonboylena.myflow.entity.core.*;
import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.impl.form.StartFormDataImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DynamicFormService {


    private static Logger logger = LoggerFactory.getLogger(DynamicFormService.class);

    private FormService formService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    public DynamicFormService(FormService formService) {
        this.formService = formService;
    }

    /**
     * 启动节点的展示
     *
     * @return
     */
    public MetaForm startProcess(ProcessDefinition processDefinition) {

        String processDefinitionId = processDefinition.getId();
        StartFormDataImpl startFormData = (StartFormDataImpl) formService.getStartFormData(processDefinitionId);
        startFormData.setProcessDefinition(null);

        MetaForm form = new MetaForm();
        form.setKey(processDefinitionId + "_0");
        List<FormProperty> formProperties = startFormData.getFormProperties();
        for (FormProperty formProperty : formProperties) {
            MetaField input = activitiType2Meta(formProperty);
            form.addMeta(input);
        }

        return form;
    }


    /**
     * 启动节点的提交
     *
     * @param processDefinitionId
     * @param userName
     * @param keyConflictCollection
     */
    public ProcessInstance submitStartForm(String processDefinitionId, String userName, KeyConflictCollection<Map<String, String>> keyConflictCollection) {
        // 流程的启动节点
        Map<String, String> stringObjectMap = keyConflictCollection.get(processDefinitionId + "_0");
        logger.trace("启动节点的内容： {}", stringObjectMap);


        ProcessInstance processInstance = null;
        try {
            identityService.setAuthenticatedUserId(userName);
            processInstance = formService.submitStartFormData(processDefinitionId, stringObjectMap);
            logger.debug("start a processInstance: {}", processInstance);
        } finally {
            identityService.setAuthenticatedUserId(null);
        }

        return processInstance;

    }

    private MetaField activitiType2Meta(FormProperty property) {

        String typeName = property.getType().getName();

        AbstractMetaItem abstractMetaItem = null;

        if ("string".equals(typeName)) {
            abstractMetaItem = new MetaItemString();
        } else if ("date".equals(typeName)) {
            abstractMetaItem = new MetaItemDate();
        } else if ("text".equals(typeName)) {
            abstractMetaItem = new MetaItemText();
        }

        if (abstractMetaItem == null) {
            logger.warn("没实现或者不认识的类型：{}, {}", typeName, property.getName());
            abstractMetaItem = new MetaItemString();
        }
        abstractMetaItem.setKey(property.getId());
        abstractMetaItem.setCaption(property.getName());

        MetaField field = new MetaField(abstractMetaItem);
        field.setReadonly(!property.isWritable());
        field.setRequired(property.isRequired());
//        field.setReadonly(property.isWritable());
        return field;


    }

}
