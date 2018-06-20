package com.github.soonboylena.myflow.dynamic.component.layout.converter;

import com.github.soonboylena.myflow.entity.core.FieldEntity;
import com.github.soonboylena.myflow.entity.core.IMeta;
import com.github.soonboylena.myflow.entity.core.MetaField;
import com.github.soonboylena.myflow.dynamic.support.WebItemType;

import java.util.Collections;
import java.util.Map;

public class StringInputConverter extends AbstractInputConverter<String, String> {

    private final transient static WebItemType type = WebItemType.StringType;

//    private final static Logger logger = LoggerFactory.getLogger(StringInputConverter.class);

    @Override
    public FieldEntity<String> pageData2Entity(IMeta meta, String data) {
        MetaField _meta = (MetaField) meta;
        return new FieldEntity<>(_meta, data);
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
