package com.github.soonboylena.myflow.Auth.bean;

import java.util.List;

public class Role extends Permission {
    private List<Permission> children;

    public boolean isRole() {
        return true;
    }
}
