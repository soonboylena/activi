package com.github.soonboylena.myflow.entity.config.builder.xml;

import com.github.soonboylena.myflow.entity.config.ConfigureHolder;
import com.github.soonboylena.myflow.entity.core.AbstractMetaItem;
import org.dom4j.Element;

import java.util.Objects;


public class XmlStringItemBuilder extends AbstractXmlItemBuilder {


    @Override
    public boolean support(Object config, ConfigureHolder holder, Object source) {
        return config != null && config instanceof Element && Objects.equals("string", ((Element) config).attributeValue("type"));
    }

    @Override
    protected AbstractMetaItem instanceItem() {
        return new AbstractMetaItem();
    }
}
