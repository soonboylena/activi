package com.github.soonboylena.activiti.component.layout.converter;

import com.github.soonboylena.activiti.support.WebItemType;
import com.github.soonboylena.entity.core.FieldEntity;
import com.github.soonboylena.entity.core.IEntity;
import com.github.soonboylena.entity.core.IMeta;
import com.github.soonboylena.entity.core.MetaField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class StringInputConverter extends AbstractInputConverter {

    private final static WebItemType type = WebItemType.StringType;

    private final static Logger logger = LoggerFactory.getLogger(StringInputConverter.class);

    @Override
    public boolean support(IMeta metaItem) {
        if (metaItem instanceof MetaField) {
            MetaField field = (MetaField) metaItem;
            return Objects.equals(field.getType(), type.literal());
        }
        return false;
    }

    @Override
    public IEntity read(IMeta meta, Object data) {
        if (data != null && !(data instanceof String)) {
            throw new IllegalArgumentException("类型不匹配。期望：String 实际：" + data.getClass().getName());
        }
        MetaField _meta = (MetaField) meta;
        return new FieldEntity<>(_meta, ((String) data));
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
