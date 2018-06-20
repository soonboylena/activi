package com.github.soonboylena.myflow.workflow.service;

import com.github.soonboylena.myflow.dynamic.component.layout.ConverterManager;
import com.github.soonboylena.myflow.dynamic.service.WebFormService;
import com.github.soonboylena.myflow.dynamic.support.KeyConflictCollection;
import com.github.soonboylena.myflow.entity.config.MemoryConfigHolder;
import com.github.soonboylena.myflow.entity.core.*;
import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.impl.form.StartFormDataImpl;
import org.activiti.engine.impl.form.TaskFormDataImpl;
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

@Service
public class DynamicFormService {


    private static Logger logger = LoggerFactory.getLogger(DynamicFormService.class);

    @Autowired
    private FormService formService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private WebFormService webFormSvs;

    // TODO： 放到服务启动去 参考 ActivitiConfigHolderListener
    @Autowired(required = false)
    private MemoryConfigHolder runtimeConfigHolder;

    @Autowired
    private ConverterManager converterManager;


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

        // TODO： 放到服务启动去
        if (runtimeConfigHolder != null) {
            runtimeConfigHolder.addMetaForm(form);
        }
        return form;
    }


    /**
     * 启动节点的提交
     *
     * @param processDefinitionId
     * @param userName
     */
    public ProcessInstance submitStartForm(String processDefinitionId, String userName, Map<String, Map<String, Object>> rawMap) {


        IEntity iEntity = webFormSvs.form2Entity(processDefinitionId + "_0", rawMap);

        Map<String, Object> data = (Map<String, Object>) iEntity.getData();
        Map<String, String> convertedData = convertStringMap(data);

        ProcessInstance processInstance;
        try {
            identityService.setAuthenticatedUserId(userName);
            processInstance = formService.submitStartFormData(processDefinitionId, convertedData);
            logger.debug("start a processInstance: {}", processInstance);
        } finally {
            identityService.setAuthenticatedUserId(null);
        }

        return processInstance;

    }

    private Map<String, String> convertStringMap(Map<String, Object> data) {

        Map<String, String> map = new HashMap<>(data.size());
        for (Map.Entry<String, Object> stringObjectEntry : data.entrySet()) {
            Object value = stringObjectEntry.getValue();
            map.put(stringObjectEntry.getKey(), String.valueOf(value));
        }
        return map;
    }


    /**
     * 任务表单的展示
     *
     * @param taskId
     */
    public FormEntity findTaskForm(String taskId) {

        TaskFormDataImpl taskFormData = (TaskFormDataImpl) formService.getTaskFormData(taskId);

        List<FormProperty> formProperties = taskFormData.getFormProperties();

        MetaForm form = new MetaForm();
        // FormEntity entity = new FormEntity(form);

        Task task = taskFormData.getTask();
        form.setCaption(task.getName());

        String taskDefinitionKey = task.getTaskDefinitionKey();
        form.setKey(task.getProcessDefinitionId() + "_" + taskDefinitionKey);

        Map<String, String> dataMap = new HashMap<>();

        for (FormProperty formProperty : formProperties) {
            MetaField input = activitiType2Meta(formProperty);
            form.addMeta(input);
            dataMap.put(input.getKey(), formProperty.getValue());
        }

        if (runtimeConfigHolder != null) {
            runtimeConfigHolder.addMetaForm(form);
        }

        KeyConflictCollection<Map<String, String>> keyConflictCollection = new KeyConflictCollection<>();
        keyConflictCollection.put(form.getKey(), dataMap);

        IEntity read = converterManager.read(form, keyConflictCollection);
        return (FormEntity) read;
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
