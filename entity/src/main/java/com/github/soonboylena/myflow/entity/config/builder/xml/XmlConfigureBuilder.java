package com.github.soonboylena.myflow.entity.config.builder.xml;

import com.github.soonboylena.myflow.entity.config.MemoryConfigHolder;
import com.github.soonboylena.myflow.entity.config.builder.ConfigureBuilder;
import com.github.soonboylena.myflow.entity.config.builder.InputItemBuilder;
import com.github.soonboylena.myflow.entity.core.MetaField;
import com.github.soonboylena.myflow.entity.core.MetaForm;
import com.github.soonboylena.myflow.entity.core.AbstractMetaItem;
import com.github.soonboylena.myflow.entity.core.MetaView;
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
            List<MetaForm> forms = elements.stream().map(e -> readForm(e, holder)).collect(Collectors.toList());
            holder.addMetaForms(forms);
        }
        if (xmlForms != null) {
            List<Element> elements = xmlForms.elements("view");
            List<MetaView> forms = elements.stream().map(e -> readView(e, holder)).collect(Collectors.toList());
            holder.addMetaViews(forms);
        }

        return holder;
    }

    /**
     * forms节点下的第二种情况： view。view是有多个form的集合；
     * view支持多个form
     *
     * @param viewElement xml元素
     * @param holder      正在构建中的holder
     * @return 构建后的MetaView
     */
    private MetaView readView(Element viewElement, MemoryConfigHolder holder) {

        MetaView view = new MetaView();
        view.setKey(viewElement.attributeValue("key"));
        view.setCaption(viewElement.attributeValue("caption"));

        logger.debug("处理view: {}, {}", view.getKey(), view.getCaption());
        List forms = viewElement.elements("form");

        logger.debug("view下定义了 {} 个form 节点", forms.size());

        for (Object form : forms) {
            Element xmlForm = (Element) form;
            String ref = xmlForm.attributeValue("ref");
            MetaForm metaForm;
            if (ref != null) {
                // 引用类型的form
                metaForm = holder.getMetaForm(ref);
                logger.debug(" 处理ref类型form 。ref：{}", ref);
                if (metaForm == null) {
                    throw new ConfigBuildException("没有找到ref: [\" + ref + \"]的指定的form。");
                }
            } else {
                metaForm = readForm(xmlForm, holder);
                logger.debug(" 读取定义类型form 。{},{}", metaForm.getKey(), metaForm.getCaption());
            }

            view.addMeta(metaForm);

            String isBusinessName = xmlForm.attributeValue("isBusinessName");
            // 设置view主form
            if (Boolean.valueOf(isBusinessName)) {
                view.setBusinessKey(metaForm.getKey());
            }
        }

        return view;
    }

    private MetaForm readForm(Element formElement, MemoryConfigHolder holder) {

        Map<String, AbstractMetaItem> metaItems = holder.getMetaItems();

        MetaForm form = new MetaForm();
        form.setKey(formElement.attributeValue("key"));
        form.setCaption(formElement.attributeValue("caption"));

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
            // isBussinessName
            String isBusinessName = xmlField.attributeValue("isBusinessName");
            Boolean bBusinessName = Boolean.valueOf(isBusinessName);
            if (bBusinessName) {
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
