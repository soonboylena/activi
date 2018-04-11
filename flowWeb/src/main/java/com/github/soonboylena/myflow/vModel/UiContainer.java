package com.github.soonboylena.myflow.vModel;

import java.util.List;

public interface UiContainer<C extends UiObject, D extends IUiDefinition> extends UiObject<D> {

    public List<C> getContents();

    public void addContent(UiObject object);

    public void setCaption(String caption);
}