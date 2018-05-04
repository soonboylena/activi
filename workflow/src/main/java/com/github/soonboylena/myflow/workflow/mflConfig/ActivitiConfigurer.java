package com.github.soonboylena.myflow.workflow.mflConfig;

import com.github.soonboylena.myflow.entity.custom.MyFlowConfigurer;
import com.github.soonboylena.myflow.entity.custom.register.UserRoleAwareRegistry;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivitiConfigurer implements MyFlowConfigurer {

    @Override
    public void addUseRoleAware(UserRoleAwareRegistry userRoleAwareRegistry) {
        userRoleAwareRegistry.addUserRoleAware(new ActivitiUserRoleAware());
    }
}
