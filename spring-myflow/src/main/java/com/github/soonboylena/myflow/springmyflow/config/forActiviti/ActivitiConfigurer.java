package com.github.soonboylena.myflow.springmyflow.config.forActiviti;

import com.github.soonboylena.myflow.entity.custom.MyFlowWebConfigurer;
import com.github.soonboylena.myflow.entity.custom.register.UserRoleAwareRegistry;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivitiConfigurer implements MyFlowWebConfigurer {

    @Override
    public void addUseRoleAware(UserRoleAwareRegistry userRoleAwareRegistry) {
        userRoleAwareRegistry.addUserRoleAware(new ActivitiUserRoleAware());
    }
}
