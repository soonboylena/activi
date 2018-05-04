package com.github.soonboylena.myflow.springmyflow.config.forActiviti;

import com.github.soonboylena.myflow.entity.custom.User;
import com.github.soonboylena.myflow.entity.custom.register.UserRoleAware;

public class ActivitiUserRoleAware implements UserRoleAware {

    @Override
    public void beforeSaveUser(User user) {

        System.out.println("-------------------------------");
    }

    @Override
    public void afterSaveUser(User user) {

        System.out.println("===============================");
    }
}
