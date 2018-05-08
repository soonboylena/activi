package com.github.soonboylena.myflow.dynamic.component.layout.converter;

import com.github.soonboylena.myflow.entity.core.FieldEntity;
import com.github.soonboylena.myflow.entity.core.IMeta;
import com.github.soonboylena.myflow.entity.core.MetaField;
import com.github.soonboylena.myflow.entity.core.MetaItemSelect;
import com.github.soonboylena.myflow.dynamic.support.WebItemType;

import java.util.Collections;
import java.util.Map;

public class SelectOneConverter extends AbstractInputConverter {

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
    public FieldEntity pageData2Entity(IMeta meta, Object data) {

        if (data != null && !(data instanceof String)) {
            throw new IllegalArgumentException("类型不匹配。期望：String 实际：" + data.getClass().getName());
        }
        MetaField _meta = (MetaField) meta;
        return new FieldEntity<>(_meta, ((String) data));
    }


}
