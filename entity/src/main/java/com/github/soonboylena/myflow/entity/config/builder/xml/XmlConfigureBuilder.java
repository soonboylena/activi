package com.github.soonboylena.myflow.entity.config.builder.xml;

import com.github.soonboylena.myflow.entity.config.MemoryConfigHolder;
import com.github.soonboylena.myflow.entity.config.builder.ConfigureBuilder;
import com.github.soonboylena.myflow.entity.config.builder.InputItemBuilder;
import com.github.soonboylena.myflow.entity.core.MetaField;
import com.github.soonboylena.myflow.entity.core.MetaForm;
import com.github.soonboylena.myflow.entity.core.AbstractMetaItem;
import com.github.soonboylena.myflow.entity.exceptions.ConfigBuildException;
import com.github.soonboylena.myflow.entity.support.XmlConfigureReader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class XmlConfigureBuilder implements ConfigureBuilder {

    private static Logger logger = LoggerFactory.getLogger(XmlConfigureBuilder.class);

    private List<InputItemBuilder> builders = new ArrayList<>();

    public XmlConfigureBuilder() {
        builders.add(new XmlStringItemBuilder());
        builders.add(new XmlSelectItemBuilder());
    }

    @Override
    public MemoryConfigHolder build(String location) throws ConfigBuildException {

        XmlConfigureReader reader = new XmlConfigureReader();
        Document xmlDocument;
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
            List<AbstractMetaItem> items = elements.stream().map(e -> readItem(e, holder, xmlDocument)).collect(Collectors.toList());
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

    private MetaForm readForm(Element formElement, Map<String, AbstractMetaItem> metaItems) {
        MetaForm form = new MetaForm();
        form.setKey(formElement.attributeValue("key"));
        form.setCaption(formElement.attributeValue("caption"));

        List fields = formElement.elements("field");
        for (Object field : fields) {

            Element xmlField = (Element) field;
            // 处理ref属性；关联到一个item
            String ref = xmlField.attributeValue("ref");
            AbstractMetaItem metaItem = metaItems.get(ref);
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
            // isBussinessName
            String isBussinessName = xmlField.attributeValue("isBussinessName");
            Boolean bBussinessName = Boolean.valueOf(isBussinessName);
            if (bBussinessName) {
                form.setBusinessKey(metaField.getKey());
            }

            form.addMeta(metaField);
        }
        return form;
    }

    private AbstractMetaItem readItem(Element s, MemoryConfigHolder holder, Document xmlDocument) {

        for (InputItemBuilder builder : this.builders) {
            if (builder.support(s, holder, xmlDocument)) {
                return builder.read(s, holder, xmlDocument);
            }
        }
        throw new ConfigBuildException("不支持的类型(转item)： " + s.attributeValue("type"));
    }

}
