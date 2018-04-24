package com.github.soonboylena.myflow.Auth.bean;

import java.util.ArrayList;
import java.util.List;

public class Role extends Permission {

    private transient final static String PREFIX = "ROLE_";
    private List<Permission> children = new ArrayList<>();

    public boolean isRole() {
        return true;
    }

    @Override
    public void setExpress(String express) {
        if (express.startsWith(PREFIX)) {
            this.express = express;
        } else {
            this.express = PREFIX + express;
        }
    }

    public void addChildren(List<Permission> permission) {
        if (permission != null) {
            children.addAll(permission);
        }
    }


}
