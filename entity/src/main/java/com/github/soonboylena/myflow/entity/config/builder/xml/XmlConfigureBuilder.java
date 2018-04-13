package com.github.soonboylena.myflow.entity.config.builder.xml;

import com.github.soonboylena.myflow.entity.config.MemoryConfigHolder;
import com.github.soonboylena.myflow.entity.config.builder.ConfigureBuilder;
import com.github.soonboylena.myflow.entity.config.builder.InputItemBuilder;
import com.github.soonboylena.myflow.entity.core.*;
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
//            List<MetaForm> forms = elements.stream().map(e -> readForm(e, holder)).collect(Collectors.toList());
//            holder.addMetaForms(forms);

            for (Element element : elements) {
                MetaForm metaForm = readForm(element, holder);
                holder.addMetaForm(metaForm);
            }
        }

        return holder;
    }

    private MetaForm readForm(Element formElement, MemoryConfigHolder holder) {

        Map<String, AbstractMetaItem> metaItems = holder.getMetaItems();

        String refAttr = formElement.attributeValue("ref");
        String formCaption = formElement.attributeValue("caption");
        if (refAttr != null && !refAttr.trim().isEmpty()) {
            MetaForm metaForm = holder.getMetaForm(refAttr);
            if (metaForm == null) {
                throw new ConfigBuildException("ref:[" + refAttr + "] 指向的form不存在。");
            }

            return new MetaFormRef(formCaption, metaForm);
        }


        MetaForm form = new MetaForm();
        form.setKey(formElement.attributeValue("key"));
        form.setCaption(formCaption);


        logger.debug("处理form: {}, {}", form.getKey(), form.getCaption());

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
            // 覆盖值
            String caption = xmlField.attributeValue("caption");
            if (caption != null) {
                metaField.setCaption(caption);
            }
            // isBusinessName
            String isBusinessName = xmlField.attributeValue("isBusinessName");
            Boolean bBusinessName = Boolean.valueOf(isBusinessName);
            if (bBusinessName) {
                form.setBusinessKey(metaField.getKey());
            }

            form.addMeta(metaField);
        }

        // form可以与其他form嵌套
        MetaForm o = readRelation(form, formElement, holder);
//        holder.addMetaForm(o);
        return o;
    }

    private MetaForm readRelation(MetaForm metaForm, Element form, MemoryConfigHolder holder) {
        Element relationsNode = form.element("relations");
        if (relationsNode == null) return metaForm;
        List relations = relationsNode.elements("relation");
        if (relations == null || relations.isEmpty()) return metaForm;
        for (Object relation : relations) {
            Element _relation = (Element) relation;
//                <relation type="contact">
            String type = _relation.attributeValue("type");
            List nextForms = _relation.elements("form");
            if (nextForms == null) {
                continue;
            }

            for (int i = 0; i < nextForms.size(); i++) {
                Element nextForm = (Element) nextForms.get(i);

                MetaForm nextMetaForm = readForm(nextForm, holder);
                nextMetaForm.setIndex(i);
                // 如果没有指定relation的类型，就用form的key来进行代替
                if (type == null || type.trim().isEmpty()) {
                    metaForm.setRelation(metaForm.getKey(), nextMetaForm);
                } else {
                    metaForm.setRelation(type, nextMetaForm);
                }
            }

        }
        return metaForm;
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
