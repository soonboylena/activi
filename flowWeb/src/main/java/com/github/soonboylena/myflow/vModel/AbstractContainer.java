package com.github.soonboylena.myflow.vModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态组件(容器）
 */
public abstract class AbstractContainer<D extends IContainerDefinition> extends AbstractDwc<D> implements UiContainer {

    // 下级节点
    @JsonProperty(value = "ui_content", index = 5)
    private List<UiObject> contents;

    public AbstractContainer() {
    }

    @JsonProperty(value = "ui_type", index = 2)
    public abstract String getType();

    public List<UiObject> getContents() {
        return contents;
    }

    public void setContents(List<UiObject> contents) {
        this.contents = contents;
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
                ", contents=" + contents +
                '}';
    }
}
