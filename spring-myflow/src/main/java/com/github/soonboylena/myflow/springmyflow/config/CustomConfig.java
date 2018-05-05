package com.github.soonboylena.myflow.springmyflow.config;

import com.github.soonboylena.myflow.entity.custom.register.UserRoleAwareRegistry;
import com.github.soonboylena.myflow.framework.service.MflUserService;
import com.github.soonboylena.myflow.persistentneo4j.repository.LoginInfoGraphRepository;
import com.github.soonboylena.myflow.persistentneo4j.service.Neo4jUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DelegatingMyFlowWebConfiguration.class)
public class CustomConfig {

    @Autowired
    LoginInfoGraphRepository loginInfoGraphRepository;

    @Autowired
    DelegatingMyFlowWebConfiguration delegatingMyFlowWebConfiguration;

    @Bean
    public UserRoleAwareRegistry userRoleAwareRegistry() {
        return delegatingMyFlowWebConfiguration.getUserRoleAwareRegistry();
    }

    @Bean
    @ConditionalOnMissingBean(MflUserService.class)
    public MflUserService mflUserService() {
        MflUserService neo4jUserService = new Neo4jUserService(loginInfoGraphRepository);
        DelegatingUserService delegatingUserService = new DelegatingUserService(neo4jUserService);
        UserRoleAwareRegistry userRoleAwareRegistry = userRoleAwareRegistry();
        delegatingUserService.setRegistry(userRoleAwareRegistry);
        return delegatingUserService;
    }

//    @Bean
//    @Primary
//    public MyFlowConfigurer myFlowWebConfigurer(DelegatingMyFlowWebConfiguration delegatingMyFlowWebConfiguration) {
//        return delegatingMyFlowWebConfiguration.getConfigurers();
//    }
}
