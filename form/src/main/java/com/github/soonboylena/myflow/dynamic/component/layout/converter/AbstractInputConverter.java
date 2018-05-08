package com.github.soonboylena.myflow.dynamic.component.layout.converter;

import com.github.soonboylena.myflow.dynamic.vModel.UiContainer;
import com.github.soonboylena.myflow.dynamic.vModel.UiObject;
import com.github.soonboylena.myflow.dynamic.vModel.uiComponent.FormItem;
import com.github.soonboylena.myflow.dynamic.vModel.uiComponent.MapUiObject;
import com.github.soonboylena.myflow.entity.core.FieldEntity;
import com.github.soonboylena.myflow.entity.core.IEntity;
import com.github.soonboylena.myflow.entity.core.IMeta;
import com.github.soonboylena.myflow.entity.core.MetaField;
import com.github.soonboylena.myflow.dynamic.support.WebItemType;

import java.util.Map;
import java.util.Objects;

public abstract class AbstractInputConverter implements UIConverter {

    @Override
    public boolean support(IMeta metaItem) {
        if (metaItem instanceof MetaField) {
            MetaField field = (MetaField) metaItem;
            return Objects.equals(field.getType().name(), getType().literal());
        }
        return false;
    }

    @Override
    public UiObject meta2Page(IMeta metaItem, UiContainer container) {

        MetaField metaInput = (MetaField) metaItem;

        FormItem labelInput = new FormItem(metaInput.getCaption());

        MetaField field = (MetaField) metaItem;
        MapUiObject<String, Object> mapUiObject = new MapUiObject<>(getType().webType());
        mapUiObject.put("name", field.getKey());
        mapUiObject.put("readonly", field.isReadOnly());
        mapUiObject.put("required", field.isRequired());


        Map<String, Object> attach = attach(metaInput);
        if (attach != null) mapUiObject.putAll(attach);

        labelInput.addContent(mapUiObject);

        if (container != null) {
            container.addContent(labelInput);
        }
        return labelInput;
    }

    protected abstract Map<String, Object> attach(MetaField metaInput);

    protected abstract WebItemType getType();

    @Override
    public void entityData2PageMap(IEntity entity, Map topMap) {
        FieldEntity fe = (FieldEntity) entity;
        topMap.put(fe.acquireMeta().getKey(), fe.getData());
    }
}
