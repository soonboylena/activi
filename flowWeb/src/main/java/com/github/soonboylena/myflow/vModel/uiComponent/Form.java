package com.github.soonboylena.myflow.vModel.uiComponent;

import com.github.soonboylena.myflow.vModel.AbstractDwc;
import com.github.soonboylena.myflow.vModel.IUiDefinition;
import lombok.Data;

public class Form extends AbstractDwc<FormDefinition> {

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

    public String getCaption() {
        return getDefine().getCaption();
    }

    @Override
    public String getType() {
        return type;
    }
}

@Data
class FormDefinition implements IUiDefinition {
    private String model;
    private String caption;
}
