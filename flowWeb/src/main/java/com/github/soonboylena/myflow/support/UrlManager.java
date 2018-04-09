package com.github.soonboylena.myflow.support;

import com.github.soonboylena.myflow.vModel.uiAction.UrlObject;
import org.springframework.http.HttpMethod;

public class UrlManager {

    private static final String prefix = "/api";

    public static UrlObject formLayout(String formKey) {
        return new UrlObject(prefix + "/page/layout/f-{formKey}", ChainMap.get().put("formKey", formKey).ok());
    }

    public static UrlObject submit(String viewKey) {
        return new UrlObject(prefix + "/page/data/{viewKey}", HttpMethod.PUT, ChainMap.get().put("viewKey", viewKey).ok());
    }

    public static UrlObject pageInit(String formKey) {
        return new UrlObject(prefix + "/page/init/{formKey}", ChainMap.get().put("formKey", formKey).ok());
    }

    public static UrlObject formInit(String formKey, Long id) {
        return new UrlObject(prefix + "/page/init/f-{formKey}/{id}"
                , ChainMap.get()
                .put("formKey", formKey)
                .put("id", id)
                .ok());
    }

    // ===============================view========================================
    public static UrlObject viewLayout(String viewKey) {
        return new UrlObject(prefix + "/page/layout/v-{viewKey}", ChainMap.get().put("viewKey", viewKey).ok());
    }
}
