package com.github.soonboylena.myflow.entity.custom;

import com.github.soonboylena.myflow.entity.custom.register.UserRoleAwareRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyFlowConfigurerComposite implements MyFlowWebConfigurer {

    private List<MyFlowWebConfigurer> delegates = new ArrayList<>();

    public void addMyFlowWebConfigures(Collection<MyFlowWebConfigurer> configurerCollections) {
        if (configurerCollections != null) {
            delegates.addAll(configurerCollections);
        }
    }

    public void addUseRoleAware(UserRoleAwareRegistry userRoleAwareRegistry) {
        for (MyFlowWebConfigurer delegate : delegates) {
            delegate.addUseRoleAware(userRoleAwareRegistry);
        }
    }
}
