package com.github.soonboylena.activiti.component.layout.converter;

import com.github.soonboylena.activiti.vModel.UiObject;
import com.github.soonboylena.entity.core.IMeta;
import com.github.soonboylena.entity.core.MetaField;
import com.github.soonboylena.entity.core.MetaItem;

public interface UIConverter {

    public boolean support(IMeta metaItem);

    public UiObject convert(IMeta metaItem);
}
