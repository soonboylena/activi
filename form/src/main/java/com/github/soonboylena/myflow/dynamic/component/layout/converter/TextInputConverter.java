package com.github.soonboylena.myflow.dynamic.component.layout.converter;

import com.github.soonboylena.myflow.dynamic.support.WebItemType;
import com.github.soonboylena.myflow.entity.core.FieldEntity;
import com.github.soonboylena.myflow.entity.core.IMeta;
import com.github.soonboylena.myflow.entity.core.MetaField;

import java.util.Collections;
import java.util.Map;

public class TextInputConverter extends AbstractInputConverter {

    private final transient static WebItemType type = WebItemType.TextType;


    @Override
    public FieldEntity pageData2Entity(IMeta meta, Object data) {
        if (data != null && !(data instanceof String)) {
            throw new IllegalArgumentException("类型不匹配。期望：String 实际：" + data.getClass().getName());
        }
        MetaField _meta = (MetaField) meta;
        return new FieldEntity<>(_meta, ((String) data));
    }

    @Override
    protected Map<String, Object> attach(MetaField metaInput) {
        return Collections.emptyMap();
    }

    @Override
    protected WebItemType getType() {
        return type;
    }

}
