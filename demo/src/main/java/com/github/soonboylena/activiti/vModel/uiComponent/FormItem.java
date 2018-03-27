package com.github.soonboylena.activiti.vModel.uiComponent;

import com.github.soonboylena.activiti.vModel.AbstractDwc;
import com.github.soonboylena.activiti.vModel.IUiDefinition;
import lombok.Data;

public class FormItem extends AbstractDwc<FormItemDefinition> {

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
class FormItemDefinition implements IUiDefinition {
    private String label;
}
