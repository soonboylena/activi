package com.github.soonboylena.myflow.vModel;

import java.util.List;

public interface UiContainer<C extends UiObject> extends UiObject {

    public List<C> getContents();
}
