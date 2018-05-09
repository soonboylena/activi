package com.github.soonboylena.myflow.entity.config.builder.xml;

import com.github.soonboylena.myflow.entity.config.ConfigureHolder;
import com.github.soonboylena.myflow.entity.core.MetaItemString;
import com.github.soonboylena.myflow.entity.core.MetaItemText;
import org.dom4j.Element;

import java.util.Objects;


public class XmlTextItemBuilder extends AbstractXmlItemBuilder {

    @Override
    public boolean support(Object config, ConfigureHolder holder, Object source) {
        return config != null && config instanceof Element && Objects.equals("text", ((Element) config).attributeValue("type"));
    }

    @Override
    protected MetaItemText instanceItem() {
        return new MetaItemText();
    }
}
