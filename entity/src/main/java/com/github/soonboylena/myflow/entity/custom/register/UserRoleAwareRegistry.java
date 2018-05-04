package com.github.soonboylena.myflow.entity.custom.register;

import java.util.ArrayList;
import java.util.List;

public class UserRoleAwareRegistry {

    private List<UserRoleAware> awares = new ArrayList<>();

    public List<UserRoleAware> getAwares() {
        return awares;
    }

    public void setAwares(List<UserRoleAware> awares) {
        this.awares = awares;
    }

    public void addUserRoleAware(UserRoleAware userRoleAware) {
        awares.add(userRoleAware);
    }
}
