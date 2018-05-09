package com.github.soonboylena.myflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = {
//        org.activiti.spring.boot.RestApiAutoConfiguration.class,
//        org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
//        org.activiti.spring.boot.SecurityAutoConfiguration.class,
//        org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class
//}
//)
@ComponentScan(basePackages = "com.github.soonboylena")
public class ActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class, args);

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
//            MflUser admin = identityService.newUser("admin");
//            admin.setPassword("admin");
//            identityService.saveUser(admin);
//
//        };
//    }
}
