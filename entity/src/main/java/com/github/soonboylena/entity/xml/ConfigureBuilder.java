package com.github.soonboylena.entity.xml;

import com.github.soonboylena.entity.config.MemoryConfigHolder;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigureBuilder {

    Map<String, Object> keyMap = new HashMap<>();

    public void build(String location) throws IOException, DocumentException {

        XmlConfigureReader reader = new XmlConfigureReader();
        Document xmlDocument = reader.read(location);

        Element rootElement = xmlDocument.getRootElement();

        MemoryConfigHolder holder = new MemoryConfigHolder();
        Element entities = rootElement.element("entities");
        if (entities != null) {
            List items = entities.elements("item");
            if (items != null) {
                for (Object item : items) {

                }
            }


        }


    }

//    private MetaItem xmlItem2Pojo(Object s) {
//    }
}
