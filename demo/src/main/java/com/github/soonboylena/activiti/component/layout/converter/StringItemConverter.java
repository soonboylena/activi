package com.github.soonboylena.activiti.component.layout.converter;

import com.github.soonboylena.activiti.vModel.UiObject;
import com.github.soonboylena.activiti.vModel.uiComponent.MapUiObject;
import com.github.soonboylena.entity.core.MetaItem;

import java.util.Objects;

public class StringItemConverter extends AbstractItemConverter {

    @Override
    public boolean support(MetaItem metaItem) {
        return Objects.equals(metaItem.getType(), "string");
    }

    @Override
    protected UiObject convertInput(MetaItem metaItem) {
        MapUiObject<String, Object> mapUiObject = new MapUiObject<>("input");
        mapUiObject.put("name", metaItem.getKey());
        return mapUiObject;
    }
}
