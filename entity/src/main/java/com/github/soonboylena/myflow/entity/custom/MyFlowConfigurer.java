package com.github.soonboylena.myflow.entity.custom;

import com.github.soonboylena.myflow.entity.custom.register.UserRoleAwareRegistry;

public interface MyFlowConfigurer {

    default public void addUseRoleAware(UserRoleAwareRegistry userRoleAwareRegistry) {
    }

}
