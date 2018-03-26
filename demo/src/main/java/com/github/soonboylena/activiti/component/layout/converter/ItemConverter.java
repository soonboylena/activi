package com.github.soonboylena.activiti.component.layout.converter;

import com.github.soonboylena.activiti.vModel.UiObject;
import com.github.soonboylena.entity.core.MetaItem;

public interface ItemConverter {

    boolean support(MetaItem metaItem);

    UiObject convert(MetaItem metaItem);
}
