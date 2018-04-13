package com.github.soonboylena.myflow.support;

import com.github.soonboylena.myflow.vModel.uiAction.UrlObject;
import org.springframework.http.HttpMethod;

public class UrlManager {

    private static final String prefix = "/api";

    //=======================================================================================================================

    /**
     * 动态表单布局
     *
     * @param formKey
     * @return
     */
    public static UrlObject formLayout(String formKey) {
        return new UrlObject(prefix + "/page/layout/{formKey}", ChainMap.get().put("formKey", formKey).ok());
    }

    /**
     * 动态表单提交
     *
     * @param formKey
     * @return
     */
    public static UrlObject submit(String formKey) {
        return new UrlObject(prefix + "/data/{viewKey}", HttpMethod.PUT, ChainMap.get().put("viewKey", formKey).ok());
    }

    /**
     * 动态表单入口
     *
     * @param formKey
     * @return
     */
    public static UrlObject pageInit(String formKey) {
        return new UrlObject(prefix + "/page/init/{formKey}", ChainMap.get().put("formKey", formKey).ok());
    }

    /**
     * 动态表单入口 （加数据）
     *
     * @param formKey
     * @param id
     * @return
     */
    public static UrlObject pageInit(String formKey, Long id) {
        return new UrlObject(prefix + "/page/init/{formKey}/{id}"
                , ChainMap.get()
                .put("formKey", formKey)
                .put("id", id)
                .ok());
    }
}
