package com.github.soonboylena.myflow;

import com.github.soonboylena.myflow.entity.config.ConfigureHolder;
import com.github.soonboylena.myflow.entity.config.RefreshHolderBuilder;
import com.github.soonboylena.myflow.entity.config.builder.ConfigureBuilder;
import com.github.soonboylena.myflow.entity.config.builder.xml.XmlConfigureBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = {
//        org.activiti.spring.boot.RestApiAutoConfiguration.class,
//        org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
//        org.activiti.spring.boot.SecurityAutoConfiguration.class,
//        org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class
//}
//)
//@ComponentScan(basePackages = "com.github.soonboylena")
public class ActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class, args);

    }

    @Bean
    public ConfigureHolder configureHolder() {

//        String path = "classpath:/entityXml/entity.xml";
        String path = "file:/home/sunb/IdeaProjects/activi/flowWeb/xml/entity.xml";
        ConfigureBuilder builder = new XmlConfigureBuilder();
        RefreshHolderBuilder refreshHolderBuilder = new RefreshHolderBuilder();
        // 支持热更新
        ConfigureHolder holder = refreshHolderBuilder.register(path, builder);
        return holder;
    }

//    @Bean
//    CommandLineRunner init(final RuntimeService runtimeService) {
//
//        return strings -> {
//            Map<String, Object> variables = new HashMap<>();
//            variables.put("applicantName", "John Doe");
//            variables.put("email", "john.doe@myflow.com");
//            variables.put("phoneNumber", "123456789");
////            runtimeService.startProcessInstanceByKey("hireProcess", variables);
//        };
//    }

//    @Bean
//    InitializingBean usersAndGroupsInitializer(final IdentityService identityService) {
//
//        return () -> {
//
//            Group group = identityService.newGroup("user");
//            group.setName("users");
//            group.setType("security-role");
//            identityService.saveGroup(group);
//
//            User admin = identityService.newUser("admin");
//            admin.setPassword("admin");
//            identityService.saveUser(admin);
//
//        };
//    }
}
