package com.github.soonboylena.myflow.entity.config.builder.xml;

import com.github.soonboylena.myflow.entity.config.ConfigureHolder;
import com.github.soonboylena.myflow.entity.config.builder.InputItemBuilder;
import com.github.soonboylena.myflow.entity.core.AbstractMetaItem;
import org.dom4j.Element;

public abstract class AbstractXmlItemBuilder implements InputItemBuilder {

    @Override
    public <T extends AbstractMetaItem> T read(Object config, ConfigureHolder holder, Object source) {
        Element element = (Element) config;
        T item = instanceItem();
        item.setCaption(element.attributeValue("caption"));
        item.setDescription(element.attributeValue("description"));
        item.setKey(element.attributeValue("key"));
        item.setName(element.attributeValue("name"));
        item.setType(element.attributeValue("type"));
        return item;
    }

    protected abstract <T extends AbstractMetaItem> T instanceItem();

}
