package com.github.soonboylena.myflow.entity.custom.register;

import com.github.soonboylena.myflow.entity.custom.MflUser;

public interface UserRoleAware {

    default void beforeSaveUser(MflUser user) {
    }


    default void afterSaveUser(MflUser user) {
    }
}
