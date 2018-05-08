package com.github.soonboylena.myflow.dynamic.vModel.uiComponent;

import com.github.soonboylena.myflow.dynamic.vModel.AbstractDwc;
import com.github.soonboylena.myflow.dynamic.vModel.IUiDefinition;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Project Name:    spring-boot-admin
 * LoginInfoEntity:            sunb
 * Date:            2017-12-08
 * Time:            13:25
 */
public class Tab extends AbstractDwc<Tab.TabDefinition> {

    private static transient final String TYPE = "m-tab";

    public Tab(String title) {
        this();
        getDefine().setTitle(title);
    }

    public Tab(String title, String key) {
        this();
        getDefine().setTitle(title);
        setKey(key);
    }

    public Tab() {
        setDefine(new TabDefinition());
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public void setKey(String key) {
        getDefine().setKey(key);
    }

    @Data
    public static class TabDefinition implements IUiDefinition {
        private String title;
        private String key;
    }
}

