package com.github.soonboylena.entity.config.builder;

import com.github.soonboylena.entity.config.MemoryConfigHolder;
import com.github.soonboylena.entity.core.MetaField;
import com.github.soonboylena.entity.core.MetaForm;
import com.github.soonboylena.entity.core.MetaItem;
import com.github.soonboylena.entity.exceptions.ConfigBuildException;
import com.github.soonboylena.entity.support.XmlConfigureReader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

public class XmlConfigureBuilder implements ConfigureBuilder {

    private static Logger logger = LoggerFactory.getLogger(XmlConfigureBuilder.class);

    @Override
    public MemoryConfigHolder build(String location) throws ConfigBuildException {

        XmlConfigureReader reader = new XmlConfigureReader();
        Document xmlDocument = null;
        try {
            xmlDocument = reader.read(location);
        } catch (IOException | DocumentException e) {
            logger.error("读取xml文件时发生异常。{}", e.getMessage());
            throw new ConfigBuildException(e);
        }

        Element rootElement = xmlDocument.getRootElement();

        MemoryConfigHolder holder = new MemoryConfigHolder();
        Element xmlItems = rootElement.element("items");
        if (xmlItems != null) {
            List<Element> elements = xmlItems.elements("item");
            List<MetaItem> items = elements.stream().map(this::readItem).collect(Collectors.toList());
            holder.addMetaItems(items);
        }


        Element xmlForms = rootElement.element("forms");
        if (xmlForms != null) {
            List<Element> elements = xmlForms.elements("form");
            List<MetaForm> forms = elements.stream().map(e -> readForm(e, holder.getMetaItems())).collect(Collectors.toList());
            holder.addMetaForms(forms);
        }


        return holder;
    }

    private MetaForm readForm(Element formElement, Map<String, MetaItem> metaItems) {
        MetaForm form = new MetaForm();
        form.setKey(formElement.attributeValue("key"));

        List fields = formElement.elements("field");
        for (Object field : fields) {

            Element xmlField = (Element) field;
            // 处理ref属性；关联到一个item
            String ref = xmlField.attributeValue("ref");
            MetaItem metaItem = metaItems.get(ref);
            if (metaItem == null) {
                throw new ConfigBuildException("没有找到ref: [" + ref + "]的指定的item。");
            }
            // 处理field自己的属性
            MetaField metaField = new MetaField(metaItem);
            String readonly = xmlField.attributeValue("readonly");
            // readonly
            Boolean bReadOnly = Boolean.valueOf(readonly);
            metaField.setReadOnly(bReadOnly);
            // required
            String required = xmlField.attributeValue("required");
            Boolean bRequired = Boolean.valueOf(required);
            metaField.setRequired(bRequired);

            form.addMeta(metaField);
        }
        return form;
    }

    private MetaItem readItem(Element s) {
        MetaItem item = new MetaItem();
        item.setCaption(s.attributeValue("caption"));
        item.setDescription(s.attributeValue("description"));
        item.setKey(s.attributeValue("key"));
        item.setName(s.attributeValue("name"));
        item.setType(s.attributeValue("type"));
        return item;
    }

}
