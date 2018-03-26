package com.github.soonboylena.activiti.component.layout.converter;

import com.github.soonboylena.activiti.vModel.UiObject;
import com.github.soonboylena.activiti.vModel.uiComponent.LabelInput;
import com.github.soonboylena.entity.core.MetaItem;

public abstract class AbstractItemConverter implements ItemConverter {

    @Override
    public UiObject convert(MetaItem metaItem) {

        LabelInput labelInput = new LabelInput(metaItem.getCaption(), 120);
        labelInput.addContent(convertInput(metaItem));
        return labelInput;
    }

    protected abstract UiObject convertInput(MetaItem metaItem);
}
