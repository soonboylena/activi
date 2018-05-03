package com.github.soonboylena.myflow.springmyflow.config;

import com.github.soonboylena.myflow.entity.custom.User;
import com.github.soonboylena.myflow.framework.service.MflUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DelegatingUserService implements MflUserService {

    private static final Logger logger = LoggerFactory.getLogger(DelegatingUserService.class);

    private MflUserService wrappedUserService;

    public DelegatingUserService(MflUserService wrappedUserService) {
        this.wrappedUserService = wrappedUserService;
    }

    @Override
    public void saveUser(User user) {
        logger.debug("delegate userService: {}", user);
        wrappedUserService.saveUser(user);
    }
}
