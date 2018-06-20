package com.github.soonboylena.myflow.workflow.config;

import com.github.soonboylena.myflow.entity.config.ConfigHolderListener;
import com.github.soonboylena.myflow.entity.config.ConfigureHolder;
import com.github.soonboylena.myflow.entity.core.MetaForm;
import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 由于不知道怎么查所有流程的所有formProperties，这个类目前实现不了
 * 这个类是希望在系统启动的时候，把通过acitvit配的所有的动态表单的字段都读进来，转成 MetaForm
 * 这样在实际运行的时候不用每次都转
 * <p>
 * 运行时可以通过 taskId取得 formProperites。但是启动时如何取这个信息还得再调查
 * <p>
 * 放开 @Compnent注解，就会把这个类就会生效
 * 类变量 metaForms 是为了处理refresh用的。需要在 ConfigHolderListener 加个 onRefresh方法；
 */

// @Component
public class ActivitiConfigHolderListener implements ConfigHolderListener {

    private static Logger logger = LoggerFactory.getLogger(ActivitiConfigHolderListener.class);


    // 组件里边留一份，配置刷新的时候不用再次处理
    private List<MetaForm> metaForms = new ArrayList<>();

    @Autowired
    private FormService formService;

    @Autowired
    private RepositoryService repositoryService;


    @Override
    public void afterBuild(ConfigureHolder holder) {

        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery().active().orderByDeploymentId().desc();
        List<ProcessDefinition> list = query.list();

        if (list == null) return;

        // 查所有的流程，有新有旧，但都是活动的
        for (ProcessDefinition processDefinition : list) {
            String id = processDefinition.getId();

            // StartForm ==> MetaForm
            StartFormData startFormData = formService.getStartFormData(id);
            MetaForm form = new MetaForm();
            form.setCaption(processDefinition.getName());
            form.setKey(id + "_0");
            List<FormProperty> formProperties = startFormData.getFormProperties();
            for (FormProperty formProperty : formProperties) {
                // MetaField input = activitiType2Meta(formProperty);
                // form.addMeta(input);
            }

            metaForms.add(form);
            holder.addMetaForm(form);


        }


    }

//     private MetaField activitiType2Meta(FormProperty property) {
//
//         String typeName = property.getType().getName();
//
//         AbstractMetaItem abstractMetaItem = null;
//
//         if ("string".equals(typeName)) {
//             abstractMetaItem = new MetaItemString();
//         } else if ("date".equals(typeName)) {
//             abstractMetaItem = new MetaItemDate();
//         } else if ("text".equals(typeName)) {
//             abstractMetaItem = new MetaItemText();
//         }
//
//         if (abstractMetaItem == null) {
//             logger.warn("没实现或者不认识的类型：{}, {}", typeName, property.getName());
//             abstractMetaItem = new MetaItemString();
//         }
//         abstractMetaItem.setKey(property.getId());
//         abstractMetaItem.setCaption(property.getName());
//
//         MetaField field = new MetaField(abstractMetaItem);
//         field.setReadonly(!property.isWritable());
//         field.setRequired(property.isRequired());
// //        field.setReadonly(property.isWritable());
//         return field;
//     }

}
