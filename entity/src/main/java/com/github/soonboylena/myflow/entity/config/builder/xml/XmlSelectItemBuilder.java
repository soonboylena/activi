package com.github.soonboylena.myflow.entity.config.builder.xml;

import com.github.soonboylena.myflow.entity.config.ConfigureHolder;
import com.github.soonboylena.myflow.entity.core.AbstractMetaItem;
import com.github.soonboylena.myflow.entity.core.MetaItemSelect;
import org.dom4j.Element;

import java.util.List;
import java.util.Objects;


public class XmlSelectItemBuilder extends AbstractXmlItemBuilder {

    @Override
    public AbstractMetaItem read(Object config, ConfigureHolder holder, Object source) {
        Element element = (Element) config;
        MetaItemSelect item = (MetaItemSelect) super.read(config, holder, source);
        List options = element.elements("option");
        if (options == null) return item;
        for (Object option : options) {
            Element _option = (Element) option;
            item.addOption(_option.attributeValue("key"), _option.getStringValue());
        }
        return item;
    }

    @Override
    protected AbstractMetaItem instanceItem() {
        return new MetaItemSelect();
    }

    @Override
    public boolean support(Object config, ConfigureHolder holder, Object source) {
        return config != null && config instanceof Element && Objects.equals("select", ((Element) config).attributeValue("type"));
    }
}
