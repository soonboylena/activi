package com.github.soonboylena.activiti.component.layout.converter;

import com.github.soonboylena.activiti.support.WebItemType;
import com.github.soonboylena.activiti.vModel.UiObject;
import com.github.soonboylena.activiti.vModel.uiComponent.MapUiObject;
import com.github.soonboylena.entity.core.IMeta;
import com.github.soonboylena.entity.core.MetaField;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class StringInputConverter extends AbstractInputConverter {

    private final static WebItemType type = WebItemType.StringType;

    @Override
    public boolean support(IMeta metaItem) {
        if (metaItem instanceof MetaField) {
            MetaField field = (MetaField) metaItem;
            return Objects.equals(field.getType(), type.literal());
        }
        return false;
    }

    @Override
    protected Map<String, Object> attach() {
        return Collections.emptyMap();
    }

    @Override
    protected WebItemType getType() {
        return type;
    }

}
