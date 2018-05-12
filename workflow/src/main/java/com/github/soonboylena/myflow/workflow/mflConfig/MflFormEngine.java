package com.github.soonboylena.myflow.workflow.mflConfig;

import com.github.soonboylena.myflow.entity.config.ConfigureHolder;
import com.github.soonboylena.myflow.entity.core.FormEntity;
import com.github.soonboylena.myflow.entity.core.MetaForm;
import com.github.soonboylena.myflow.entity.core.Relation;
import com.github.soonboylena.myflow.entity.exceptions.NoMatchedKeyException;
import com.github.soonboylena.myflow.framework.web.FormQueryService;
import com.github.soonboylena.myflow.workflow.utils.WorkFlowUtil;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.form.FormEngine;
import org.activiti.engine.impl.form.JuelFormEngine;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.scripting.ScriptBindingsFactory;
import org.activiti.engine.impl.scripting.ScriptingEngines;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.Bindings;
import java.util.List;
import java.util.Map;

import static com.github.soonboylena.myflow.workflow.utils.WorkFlowUtil.formKeySuffix;

public class MflFormEngine implements FormEngine {

    private static final Logger logger = LoggerFactory.getLogger(MflFormEngine.class);

    private JuelFormEngine origEngine = new JuelFormEngine();

    private ConfigureHolder configHolder;

    private FormQueryService queryService;

    public MflFormEngine(ConfigureHolder configHolder, FormQueryService queryService) {
        this.configHolder = configHolder;
        this.queryService = queryService;
    }

    @Override
    public String getName() {
        // 作为默认的engine实现, 所以不返回 EngineName
        return null;
    }

    @Override
    public Object renderStartForm(StartFormData startForm) {
        String formKey = startForm.getFormKey();

        if (formKey != null && formKey.endsWith(formKeySuffix)) {

            String noSuffixFormKey = formKey.replace(formKeySuffix, "");
            logger.info("使用动态表单引擎处理. metaKey:{}", noSuffixFormKey);
            MetaForm metaForm = configHolder.getMetaForm(noSuffixFormKey);
            if (metaForm == null) {
                throw new NoMatchedKeyException(noSuffixFormKey, "form");
            }
            return new FormEntity(metaForm);
        }

        return origEngine.renderStartForm(startForm);
    }

    @Override
    public Object renderTaskForm(TaskFormData taskForm) {
        String formKey = taskForm.getFormKey();


        if (formKey != null && formKey.endsWith(formKeySuffix)) {

            String noSuffixFormKey = formKey.replace(formKeySuffix, "");
            logger.info("使用动态表单引擎处理. metaKey:{}", noSuffixFormKey);
            MetaForm metaForm = configHolder.getMetaForm(noSuffixFormKey);
            if (metaForm == null) {
                throw new NoMatchedKeyException(noSuffixFormKey, "form");
            }

            // 先构造一个空的formEntity
            FormEntity formEntity = new FormEntity(metaForm);

            // 从表单中取得变量
            ScriptingEngines scriptingEngines = Context.getProcessEngineConfiguration().getScriptingEngines();
            ScriptBindingsFactory scriptBindingsFactory = scriptingEngines.getScriptBindingsFactory();
            Bindings bindings = scriptBindingsFactory.createBindings(((TaskEntity) taskForm.getTask()).getExecution());

            // 这些变量里边有哪些是表单
            Map<String, Long> formKeyByPattern = WorkFlowUtil.findFormKeyByPattern(bindings);

            // 查看现在要看的这个form里边，包含了哪些form。如果被包含的form能跟前边匹配上就把值取出来放进去
            // 先不递归查找了。就第一层。以后如果有需要再处理
            logger.trace("扫描表单 {} 包含其他表单的情况", metaForm.getKey());
            for (Relation relation : metaForm.getRelations()) {
                List<MetaForm> relatedForms = relation.getRelatedForm();
                for (MetaForm relatedForm : relatedForms) {
                    String relatedFormKey = relatedForm.getKey();
                    logger.trace("关系: {}, 关联的form: {}-{}", relation.getType(), relatedForm.getKey(), relatedForm.getCaption());
                    if (formKeyByPattern.containsKey(relatedFormKey)) {
                        Long relatedFormId = formKeyByPattern.get(relatedFormKey);
                        logger.debug("找到了匹配的form。key：{}, id:{}", relatedFormKey, relatedFormId);

                        logger.trace("从数据库取对应form的内容");
                        FormEntity byId = queryService.findById(relatedForm, relatedFormId);

                        // 假设：formEntity的relations结构跟meta的一样。而且能匹配的form一种类型里边只有一个
                        // 这里有可能有问题
                        logger.debug("将流程前步骤form保存到当前form中");
                        formEntity.getRelations(relation.getType()).set(0, byId);
                    }
                }
            }
            return formEntity;

        }

        return origEngine.renderTaskForm(taskForm);
    }
}
