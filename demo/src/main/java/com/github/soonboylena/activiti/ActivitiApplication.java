package com.github.soonboylena.activiti;

import com.github.soonboylena.entity.config.ConfigureHolder;
import com.github.soonboylena.entity.config.builder.ConfigureBuilder;
import com.github.soonboylena.entity.config.builder.XmlConfigureBuilder;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class, args);

    }

    @Bean
    public ConfigureHolder configureHolder() {
        ConfigureBuilder builder = new XmlConfigureBuilder();
        return builder.build("classpath:entity.xml");
    }

    @Bean
    CommandLineRunner init(final RuntimeService runtimeService) {

        return strings -> {
            Map<String, Object> variables = new HashMap<>();
            variables.put("applicantName", "John Doe");
            variables.put("email", "john.doe@activiti.com");
            variables.put("phoneNumber", "123456789");
            runtimeService.startProcessInstanceByKey("hireProcess", variables);
        };
    }

    @Bean
    InitializingBean usersAndGroupsInitializer(final IdentityService identityService) {

        return () -> {

            Group group = identityService.newGroup("user");
            group.setName("users");
            group.setType("security-role");
            identityService.saveGroup(group);

            User admin = identityService.newUser("admin");
            admin.setPassword("admin");
            identityService.saveUser(admin);

        };
    }
}
