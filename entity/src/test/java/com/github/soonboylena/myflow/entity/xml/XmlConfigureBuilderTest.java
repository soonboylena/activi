package com.github.soonboylena.myflow.entity.xml;

import com.github.soonboylena.myflow.entity.config.ConfigureHolder;
import com.github.soonboylena.myflow.entity.config.builder.ConfigureBuilder;
import com.github.soonboylena.myflow.entity.config.builder.xml.XmlConfigureBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class XmlConfigureBuilderTest {

    private ConfigureBuilder configureBuilder;

    @Before
    public void init() {
        configureBuilder = new XmlConfigureBuilder();
    }

    @Test
    public void build() {
        ConfigureHolder build = configureBuilder.build("classpath:entity.xml");
        Assert.assertNotNull(build);
        Assert.assertNotNull(build.getMetaItems());
        Assert.assertNotEquals("item定义不为空", build.getMetaItems().size(), 0);

        Assert.assertNotEquals("form定义信息不为空。", build.getMetaForms().size(), 0);
        System.out.println(build);
    }
}