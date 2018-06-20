package com.github.soonboylena.myflow.dynamic.component.layout.converter;

import com.github.soonboylena.myflow.dynamic.support.WebItemType;
import com.github.soonboylena.myflow.entity.core.FieldEntity;
import com.github.soonboylena.myflow.entity.core.IEntity;
import com.github.soonboylena.myflow.entity.core.IMeta;
import com.github.soonboylena.myflow.entity.core.MetaField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

public class DateInputConverter extends AbstractInputConverter {

    private final transient static WebItemType type = WebItemType.DateType;

//    private final static Logger logger = LoggerFactory.getLogger(StringInputConverter.class);

    @Override
    public FieldEntity<LocalDate> pageData2Entity(IMeta meta, Object data) {
        if (data != null && !(data instanceof String)) {
            throw new IllegalArgumentException("类型不匹配。期望： Date 实际：" + data.getClass().getName());
        }
        MetaField _meta = (MetaField) meta;

        LocalDate date = null;
        if (data != null) {
            if (data.toString().matches("\\d{4}-\\d{2}-\\d{2}T.+")) {
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.SIMPLIFIED_CHINESE);
                date = LocalDate.parse(String.valueOf(data), inputFormatter);
            } else {
                date = LocalDate.parse(String.valueOf(data));
            }
        }

        return new FieldEntity<>(_meta, date);
    }

    @Override
    public void entityData2PageMap(IEntity entity, Map topMap) {
        FieldEntity fe = (FieldEntity) entity;
        Object data = fe.getData();

        if (data != null) {
            topMap.put(fe.acquireMeta().getKey(), data);
        }
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
