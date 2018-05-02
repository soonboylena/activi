package com.github.soonboylena.myflow.Auth.bean;

import com.github.soonboylena.myflow.entity.custom.Permission;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Menu {

    private Long id;
    private String code;
    private String url;
    private String title;
    private String picUrl;
    private String icon;
    private String description;
    private int menuOrder = 0;

    List<Menu> children = new ArrayList<>();
    private Permission auth;

    public void addSubMenu(Menu item) {
        children.add(item);
    }

    public void setAuth(Permission auth) {
        this.auth = auth;
    }

    public boolean isLeaf() {
        return children == null || children.isEmpty();
    }
}
