package com.github.soonboylena.myflow.Auth.bean;

import com.github.soonboylena.myflow.entity.custom.Permission;
import com.github.soonboylena.myflow.entity.custom.Role;

import java.util.List;

public class WebRole extends Role {

    private transient final static String PREFIX = "ROLE_";

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

    public void addPermissions(List<Permission> permission) {
        if (permission != null) {
            permissions.addAll(permission);
        }
    }


}
