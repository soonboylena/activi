package com.github.soonboylena.myflow.component.layout.converter;

import com.github.soonboylena.myflow.entity.core.FieldEntity;
import com.github.soonboylena.myflow.entity.core.IMeta;
import com.github.soonboylena.myflow.entity.core.MetaField;
import com.github.soonboylena.myflow.entity.core.MetaItemResource;
import com.github.soonboylena.myflow.support.UrlManager;
import com.github.soonboylena.myflow.support.WebItemType;

import java.util.HashMap;
import java.util.Map;

public class ResourceInputConverter extends AbstractInputConverter {

    private final transient static WebItemType type = WebItemType.ResourceType;

//    private final static Logger logger = LoggerFactory.getLogger(StringInputConverter.class);

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

        MetaItemResource metaItem = (MetaItemResource) metaInput.getMetaItem();
        Map<String, Object> map = new HashMap<>();
        map.put("url", UrlManager.getResources(metaItem.getKey()));
        map.put("key", metaInput.getKey());
        return map;
    }

    @Override
    protected WebItemType getType() {
        return type;
    }

}
