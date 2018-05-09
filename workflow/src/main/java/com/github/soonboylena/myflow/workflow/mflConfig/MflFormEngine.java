package com.github.soonboylena.myflow.workflow.mflConfig;

import com.github.soonboylena.myflow.entity.config.ConfigureHolder;
import com.github.soonboylena.myflow.entity.core.MetaForm;
import com.github.soonboylena.myflow.entity.exceptions.NoMatchedKeyException;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.form.FormEngine;
import org.activiti.engine.impl.form.JuelFormEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.soonboylena.myflow.workflow.utils.WorkFlowUtil.formKeySuffix;

public class MflFormEngine implements FormEngine {

    private static final Logger logger = LoggerFactory.getLogger(MflFormEngine.class);

    private JuelFormEngine origEngine = new JuelFormEngine();

    private ConfigureHolder configHolder;

    public MflFormEngine(ConfigureHolder configHolder) {
        this.configHolder = configHolder;
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
            return metaForm;
        }

        return origEngine.renderStartForm(startForm);
    }

    @Override
    public Object renderTaskForm(TaskFormData taskForm) {
        String formKey = taskForm.getFormKey();
        if (formKey != null && formKey.endsWith(formKeySuffix)) {
            System.out.println("需要自己跟form关联的内容");
            return "xxxxxxxxxxx";
        }

        return origEngine.renderTaskForm(taskForm);
    }
}
