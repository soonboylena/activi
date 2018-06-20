package com.github.soonboylena.myflow.springmyflow.config;

import com.github.soonboylena.myflow.entity.config.ConfigureHolder;
import com.github.soonboylena.myflow.entity.config.ConfigHolderListener;
import com.github.soonboylena.myflow.entity.config.MemoryConfigHolder;
import com.github.soonboylena.myflow.entity.config.RefreshHolderBuilder;
import com.github.soonboylena.myflow.entity.config.builder.ConfigureBuilder;
import com.github.soonboylena.myflow.entity.config.builder.xml.XmlConfigureBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Configuration
public class CommonConfig {

    @Autowired(required = false)
    private List<ConfigHolderListener> configHolderListeners;


    @Bean
    @Primary
    public ConfigureHolder configureHolder() {

//        String path = "classpath:/entityXml/entity.xml";
        String path = "file:/home/sunb/IdeaProjects/activi/flowWeb/xml/entity.xml";
        ConfigureBuilder builder = new XmlConfigureBuilder();
        RefreshHolderBuilder refreshHolderBuilder = new RefreshHolderBuilder();
        // 支持热更新
        ConfigureHolder holder = refreshHolderBuilder.register(path, builder, configHolderListeners);

        return holder;
    }

    @Bean
    public MemoryConfigHolder runtimeConfigHolder() {
        return new MemoryConfigHolder();
    }
}
