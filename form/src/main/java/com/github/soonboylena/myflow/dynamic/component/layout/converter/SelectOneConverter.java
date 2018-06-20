package com.github.soonboylena.myflow.dynamic.component.layout.converter;

import com.github.soonboylena.myflow.entity.core.FieldEntity;
import com.github.soonboylena.myflow.entity.core.IMeta;
import com.github.soonboylena.myflow.entity.core.MetaField;
import com.github.soonboylena.myflow.entity.core.MetaItemSelect;
import com.github.soonboylena.myflow.dynamic.support.WebItemType;

import java.util.Collections;
import java.util.Map;

public class SelectOneConverter extends AbstractInputConverter<String, String> {

    private final static WebItemType type = WebItemType.SelectType;

    @Override
    protected Map<String, Object> attach(MetaField metaInput) {
        MetaItemSelect metaItem = (MetaItemSelect) metaInput.getMetaItem();
        return Collections.singletonMap("options", metaItem.getOptions());
    }

    @Override
    protected WebItemType getType() {
        return type;
    }

    @Override
    public FieldEntity<String> pageData2Entity(IMeta meta, String data) {

        MetaField _meta = (MetaField) meta;
        return new FieldEntity<>(_meta, data);
    }


}
