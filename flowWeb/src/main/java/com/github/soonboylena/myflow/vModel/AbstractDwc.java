package com.github.soonboylena.myflow.vModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 动态组件
 */
public abstract class AbstractDwc<D extends IUiDefinition> implements UiContainer, Serializable {

    // id
    @JsonProperty(value = "ui_id", index = 1)
    private String id;
    // 下级节点
    @JsonProperty(value = "ui_content", index = 5)
    private List<UiObject> contents;
    // 定义
    @JsonProperty(value = "ui_define", index = 4)
    private D define;

    private String caption;

    public AbstractDwc() {
    }

    @JsonProperty(value = "ui_type", index = 2)
    public abstract String getType();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<UiObject> getContents() {
        return contents;
    }

    public void setContents(List<UiObject> contents) {
        this.contents = contents;
    }

    public D getDefine() {
        return define;
    }

    public void setDefine(D define) {
        this.define = define;
    }

    public String getCaption() {
        return caption;
    }

    @Override
    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void addContent(UiObject dwc) {

        if (this.contents == null) {
            contents = new ArrayList<>();
        }
        contents.add(dwc);
    }

    @Override
    public String toString() {
        return "AbstractDwc{" +
                "id='" + id + '\'' +
                ", contents=" + contents +
                ", define=" + define +
                '}';
    }
}
