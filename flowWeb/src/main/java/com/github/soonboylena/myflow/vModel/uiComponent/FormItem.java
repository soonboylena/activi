package com.github.soonboylena.myflow.vModel.uiComponent;

import com.github.soonboylena.myflow.vModel.AbstractContainer;
import com.github.soonboylena.myflow.vModel.AbstractContainerDefinition;
import com.github.soonboylena.myflow.vModel.AbstractDwc;
import com.github.soonboylena.myflow.vModel.IUiDefinition;
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
