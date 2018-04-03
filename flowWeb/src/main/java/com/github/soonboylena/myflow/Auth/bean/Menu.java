package com.github.soonboylena.myflow.Auth.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Menu {

    private String code;
    private String url;
    private String title;
    private String picUrl;
    private String icon;
    private String description;

    List<Menu> children = new ArrayList<>();

    public void addSubMenu(Menu item) {
        children.add(item);
    }
}
