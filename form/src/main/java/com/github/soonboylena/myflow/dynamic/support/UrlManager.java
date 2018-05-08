package com.github.soonboylena.myflow.dynamic.support;

import com.github.soonboylena.myflow.dynamic.vModel.uiAction.UrlObject;
import org.springframework.http.HttpMethod;

public class UrlManager {

    public static final String prefix = "/api";

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
     * 动态表单入口 （数据）
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

    /**
     * 动态表单数据
     *
     * @param formKey
     * @return
     */
    public static UrlObject data(String formKey, Long id) {
        return new UrlObject(prefix + "/data/{formKey}/{id}", HttpMethod.GET, ChainMap.get().put("formKey", formKey).put("id", id).ok());
    }

    /**
     * 一览画面数据
     *
     * @param formKey
     * @return
     */
    public static UrlObject dataList(String formKey) {
        return new UrlObject(prefix + "/data/list/{formKey}", HttpMethod.GET, ChainMap.get().put("formKey", formKey).ok());
    }

    /**
     * 取得resource下拉一览
     *
     * @param formKey
     * @return
     */
    public static UrlObject getResources(String formKey) {
        return new UrlObject(prefix + "/data/key/{formKey}/resources", HttpMethod.GET, ChainMap.get().put("formKey", formKey).ok());
    }


    //=====================================
    //====================流程=============
    //=====================================

    /**
     * 流程入口
     *
     * @param processId
     * @return
     */
    public static UrlObject processInit(String processId) {
        return new UrlObject(prefix + "/process/{processId}/init", ChainMap.get().put("processId", processId).ok());
    }

    /**
     * 流程表单布局
     *
     * @param processId
     * @return
     */
    public static UrlObject processLayout(String processId) {
        return new UrlObject(prefix + "/process/{processId}/start/layout"
                , ChainMap.get()
                .put("processId", processId)
                .ok());
    }

    public static UrlObject processStart(String processId) {
        return new UrlObject(prefix + "/process/start/{processId}"
                , HttpMethod.PUT
                , ChainMap.get()
                .put("processId", processId)
                .ok());
    }
}
