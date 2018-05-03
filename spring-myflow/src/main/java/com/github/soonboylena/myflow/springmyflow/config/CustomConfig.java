package com.github.soonboylena.myflow.springmyflow.config;

import com.github.soonboylena.myflow.framework.service.MflUserService;
import com.github.soonboylena.myflow.persistentneo4j.repository.LoginInfoGraphRepository;
import com.github.soonboylena.myflow.persistentneo4j.service.Neo4jUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfig {

    @Autowired
    LoginInfoGraphRepository loginInfoGraphRepository;

    @Bean
    @ConditionalOnMissingBean(MflUserService.class)
    public MflUserService mflUserService() {
        MflUserService neo4jUserService = new Neo4jUserService(loginInfoGraphRepository);
        return new DelegatingUserService(neo4jUserService);
    }
}
