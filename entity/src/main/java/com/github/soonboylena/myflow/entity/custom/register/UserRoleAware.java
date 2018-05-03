package com.github.soonboylena.myflow.entity.custom.register;

import com.github.soonboylena.myflow.entity.custom.User;

public interface UserRoleAware {

    public void beforeSaveUser(User user);

}
