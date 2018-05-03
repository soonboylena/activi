package com.github.soonboylena.myflow.springmyflow.config;


import com.github.soonboylena.myflow.entity.custom.MyFlowConfigurerComposite;
import com.github.soonboylena.myflow.entity.custom.MyFlowWebConfigurer;
import com.github.soonboylena.myflow.entity.custom.register.UserRoleAwareRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 利用spring的注解方式进行配置；
 */
@Configuration
public class DelegatingMyFlowWebConfiguration {

    private final MyFlowConfigurerComposite configurers = new MyFlowConfigurerComposite();
    private UserRoleAwareRegistry userRoleAwareRegistry = new UserRoleAwareRegistry();


    @Autowired(required = false)
    public void setConfigurers(List<MyFlowWebConfigurer> configurers) {
        if (!CollectionUtils.isEmpty(configurers)) {
            this.configurers.addMyFlowWebConfigures(configurers);
        }
    }

    public void registryUserRoleAware(UserRoleAwareRegistry userRoleAwareRegistry) {
        configurers.addUseRoleAware(userRoleAwareRegistry);
    }

    public MyFlowConfigurerComposite getConfigurers() {
        return configurers;
    }

    public UserRoleAwareRegistry getUserRoleAwareRegistry() {
        if (this.userRoleAwareRegistry == null) userRoleAwareRegistry = new UserRoleAwareRegistry();
        return userRoleAwareRegistry;
    }
}
