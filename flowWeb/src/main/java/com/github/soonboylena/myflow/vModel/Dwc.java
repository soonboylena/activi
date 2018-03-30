package com.github.soonboylena.myflow.vModel;

import java.util.List;

public interface Dwc<C extends UiObject, D extends IUiDefinition> extends UiObject {

    public String getType();

    public D getDefine();

    public List<C> getContents();
}
