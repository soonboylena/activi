package com.github.soonboylena.activiti.vModel;

import java.util.List;

public interface Dwc<C extends UiObject, D extends IUiDefinition> extends UiObject {

    public String getType();

    public D getDefine();

    public List<C> getContents();

    public String getForm();
}
