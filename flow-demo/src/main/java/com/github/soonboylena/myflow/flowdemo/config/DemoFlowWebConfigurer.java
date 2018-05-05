package com.github.soonboylena.myflow.flowdemo.config;

import com.github.soonboylena.myflow.entity.custom.MyFlowConfigurer;
import com.github.soonboylena.myflow.entity.custom.register.UserRoleAwareRegistry;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoFlowWebConfigurer implements MyFlowConfigurer {

    @Override
    public void addUseRoleAware(UserRoleAwareRegistry userRoleAwareRegistry) {
        System.out.println("将计就计");
    }
}
