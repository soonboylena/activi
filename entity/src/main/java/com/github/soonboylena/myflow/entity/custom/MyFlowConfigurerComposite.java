package com.github.soonboylena.myflow.entity.custom;

import com.github.soonboylena.myflow.entity.custom.register.UserRoleAwareRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyFlowConfigurerComposite implements MyFlowConfigurer {

    private List<MyFlowConfigurer> delegates = new ArrayList<>();

    public void addMyFlowWebConfigures(Collection<MyFlowConfigurer> configurerCollections) {
        if (configurerCollections != null) {
            delegates.addAll(configurerCollections);
        }
    }

    public void addUseRoleAware(UserRoleAwareRegistry userRoleAwareRegistry) {
        for (MyFlowConfigurer delegate : delegates) {
            delegate.addUseRoleAware(userRoleAwareRegistry);
        }
    }
}
