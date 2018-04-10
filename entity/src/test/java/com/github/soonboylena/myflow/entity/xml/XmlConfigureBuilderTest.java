package com.github.soonboylena.myflow.entity.xml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.soonboylena.myflow.entity.config.ConfigureHolder;
import com.github.soonboylena.myflow.entity.config.builder.ConfigureBuilder;
import com.github.soonboylena.myflow.entity.config.builder.xml.XmlConfigureBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class XmlConfigureBuilderTest {

    private ConfigureBuilder configureBuilder;
    protected ObjectMapper mapper = new ObjectMapper();

    @Before
    public void init() {
        configureBuilder = new XmlConfigureBuilder();
    }

    @Test
    public void build() {
        ConfigureHolder build = configureBuilder.build("file:/home/sunb/IdeaProjects/activi/entity/src/main/resources/entity.xml");
        Assert.assertNotNull(build);
        Assert.assertNotNull(build.getMetaItems());
        Assert.assertNotEquals("item定义不为空", build.getMetaItems().size(), 0);

        Assert.assertNotEquals("form定义信息不为空。", build.getMetaForms().size(), 0);
        print(build, "配置");
    }

    protected void print(Object build, String title) {

        try {
            System.out.println("===================================");
            System.out.println("=" + title);
            System.out.println("===================================");

            String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(build);
            System.out.println(indented);
        } catch (Exception e) {
            System.out.println("转json的时候挂了。");
            System.out.println(e.getMessage());
            System.out.println("原始:  " + build);
        }
    }
}