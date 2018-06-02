package com.github.soonboylena.myflow.workflow.formType;

import org.activiti.engine.form.AbstractFormType;

public class TextFormType extends AbstractFormType {

    public static final String TEXT_FORM_TYPE_NAME = "text";

    @Override
    public Object convertFormValueToModelValue(String propertyValue) {
        return propertyValue;
    }

    @Override
    public String convertModelValueToFormValue(Object modelValue) {
        return (String) modelValue;
    }

    @Override
    public String getName() {
        return TEXT_FORM_TYPE_NAME;
    }
}
