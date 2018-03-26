package com.github.soonboylena.entity.xml;

import com.github.soonboylena.entity.config.MemoryConfigHolder;
import com.github.soonboylena.entity.config.exception.ConfigBuildException;
import com.github.soonboylena.entity.core.MetaForm;
import com.github.soonboylena.entity.core.MetaItem;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class XmlConfigureBuilder {


    public MemoryConfigHolder build(String location) throws IOException, DocumentException {

        XmlConfigureReader reader = new XmlConfigureReader();
        Document xmlDocument = reader.read(location);

        Element rootElement = xmlDocument.getRootElement();

        MemoryConfigHolder holder = new MemoryConfigHolder();
        Element entities = rootElement.element("entities");
        if (entities != null) {
            List<Element> elements = entities.elements("item");
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
            String ref = xmlField.attributeValue("ref");
            MetaItem metaItem = metaItems.get(ref);
            if (metaItem == null) {
                throw new ConfigBuildException("没有找到ref: [" + ref + "]的指定的item。");
            }
            form.addField(metaItem);
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
