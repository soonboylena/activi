package com.github.soonboylena.myflow.entity.custom.register;

import com.github.soonboylena.myflow.entity.custom.User;

public interface UserRoleAware {

    default void beforeSaveUser(User user) {
    }


    default void afterSaveUser(User user) {
    }
}
