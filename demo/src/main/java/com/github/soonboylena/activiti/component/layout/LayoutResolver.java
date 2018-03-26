package com.github.soonboylena.activiti.component.layout;

import com.github.soonboylena.activiti.vModel.UiObject;
import com.github.soonboylena.entity.core.MetaForm;

public interface LayoutResolver {

    UiObject arrangeAsForm(MetaForm metaForm);
}
