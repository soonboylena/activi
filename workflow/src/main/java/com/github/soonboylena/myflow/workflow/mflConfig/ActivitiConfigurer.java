package com.github.soonboylena.myflow.workflow.mflConfig;

import com.github.soonboylena.myflow.entity.custom.MyFlowConfigurer;
import com.github.soonboylena.myflow.entity.custom.register.UserRoleAwareRegistry;
import org.activiti.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivitiConfigurer implements MyFlowConfigurer {

    @Autowired
    private IdentityService identityService;

    @Override
    public void addUseRoleAware(UserRoleAwareRegistry userRoleAwareRegistry) {
        userRoleAwareRegistry.addUserRoleAware(new ActivitiUserRoleAware(identityService));
    }
}
