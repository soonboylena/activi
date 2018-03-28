package com.github.soonboylena.activiti.support;

import com.github.soonboylena.activiti.vModel.uiAction.UrlObject;

public class UrlManager {

    private static final String prefix = "/menu";

    public static UrlObject layout(String formKey) {
        return new UrlObject(prefix + "/page/layout/{formKey}", ChainMap.get().put("formKey", formKey).ok());
    }
}
