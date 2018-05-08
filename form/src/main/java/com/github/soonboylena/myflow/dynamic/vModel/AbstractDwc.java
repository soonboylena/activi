package com.github.soonboylena.myflow.dynamic.vModel;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 动态组件
 */
public abstract class AbstractDwc<D extends IUiDefinition> implements UiObject, Serializable {

    // 定义
    @JsonProperty(value = "ui_define", index = 4)
    private D define;

    public AbstractDwc() {
    }

    @JsonProperty(value = "ui_type", index = 2)
    public abstract String getType();


    public D getDefine() {
        return define;
    }

    public void setDefine(D define) {
        this.define = define;
    }

    @Override
    public String toString() {
        return "AbstractDwc{" +
                "define=" + define +
                '}';
    }
}
