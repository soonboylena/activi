package com.github.soonboylena.myflow.springmyflow.config;

import com.github.soonboylena.myflow.entity.custom.MflUser;
import com.github.soonboylena.myflow.entity.custom.register.UserRoleAware;
import com.github.soonboylena.myflow.entity.custom.register.UserRoleAwareRegistry;
import com.github.soonboylena.myflow.framework.web.MflUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DelegatingUserService implements MflUserService {

    private static final Logger logger = LoggerFactory.getLogger(DelegatingUserService.class);

    private MflUserService wrappedUserService;

    private UserRoleAwareRegistry registry;


    public DelegatingUserService(MflUserService wrappedUserService) {
        this.wrappedUserService = wrappedUserService;
    }

    @Override
    public MflUser saveUser(MflUser user) {

        logger.debug("delegate userService: {}", user);
        MflUser savedUser;
        if (registry == null) {

            logger.debug("实际执行： {}", wrappedUserService.getClass().getName());
            savedUser = wrappedUserService.saveUser(user);
            return savedUser;
        } else {
            List<UserRoleAware> awares = registry.getAwares();
            for (UserRoleAware aware : awares) {
                logger.debug("前置处理： {}", aware.getClass().getName());
                aware.beforeSaveUser(user);
            }
            logger.debug("实际执行： {}", wrappedUserService.getClass().getName());
            savedUser = wrappedUserService.saveUser(user);

            for (UserRoleAware aware : awares) {
                logger.debug("后置处理： {}", aware.getClass().getName());
                aware.afterSaveUser(savedUser);
            }
        }
        return savedUser;
    }

    public void setRegistry(UserRoleAwareRegistry registry) {
        this.registry = registry;
    }
}
