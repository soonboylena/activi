package com.github.soonboylena.myflow.vModel.uiComponent;

import com.github.soonboylena.myflow.vModel.AbstractContainer;
import com.github.soonboylena.myflow.vModel.AbstractContainerDefinition;
import com.github.soonboylena.myflow.vModel.IUiDefinition;
import lombok.Data;

public class Form extends AbstractContainer<FormDefinition> {

    private final static String type = "nForm";

    public Form(String model) {
        this(model, null);
    }

    public Form(String model, String caption) {
        FormDefinition definition = new FormDefinition();
        definition.setModel(model);
        if (caption != null) definition.setCaption(caption);
        setDefine(definition);
    }


    @Override
    public String getType() {
        return type;
    }
}

@Data
class FormDefinition  extends AbstractContainerDefinition {
    private String model;
    private String caption;
}
