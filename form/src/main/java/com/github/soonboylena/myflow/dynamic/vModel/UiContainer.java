package com.github.soonboylena.myflow.dynamic.vModel;

import java.util.List;

public interface UiContainer<C extends UiObject, D extends IContainerDefinition> extends UiObject<D> {

    public List<C> getContents();

    public void addContent(UiObject object);

    public default void setCaption(String caption) {
        D define = getDefine();
        if (define != null) {
            define.setCaption(caption);
        }
    }

}