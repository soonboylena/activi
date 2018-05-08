package com.github.soonboylena.myflow.dynamic.vModel.uiComponent;

import com.github.soonboylena.myflow.dynamic.vModel.AbstractContainer;
import com.github.soonboylena.myflow.dynamic.vModel.AbstractContainerDefinition;
import lombok.Data;

public class FormItem extends AbstractContainer<FormItemDefinition> {

    private static final String type = "FormItem";

    public FormItem(String lable) {
        FormItemDefinition definition = new FormItemDefinition();
        definition.setLabel(lable);
        setDefine(definition);
    }

    @Override
    public String getType() {
        return type;
    }

}

@Data
class FormItemDefinition extends AbstractContainerDefinition {
    private String label;
}
