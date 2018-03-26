package com.github.soonboylena.entity.xml;

import com.github.soonboylena.entity.config.MemoryConfigHolder;
import org.dom4j.DocumentException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class XmlConfigureBuilderTest {

    private XmlConfigureBuilder configureBuilder;

    @Before
    public void init() {
        configureBuilder = new XmlConfigureBuilder();
    }

    @Test
    public void build() throws IOException, DocumentException {
        MemoryConfigHolder build = configureBuilder.build("classpath:entity.xml");
        Assert.assertNotNull(build);
        Assert.assertNotNull(build.getMetaItems());
        Assert.assertNotEquals("item定义不为空", build.getMetaItems().size(), 0);

        Assert.assertNotEquals("form定义信息不为空。", build.getMetaForms().size(), 0);
        System.out.println(build);
    }
}