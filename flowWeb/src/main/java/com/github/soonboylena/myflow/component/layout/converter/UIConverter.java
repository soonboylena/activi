package com.github.soonboylena.myflow.component.layout.converter;

import com.github.soonboylena.myflow.vModel.UiObject;
import com.github.soonboylena.myflow.entity.core.IEntity;
import com.github.soonboylena.myflow.entity.core.IMeta;
import com.github.soonboylena.myflow.entity.core.MetaField;
import com.github.soonboylena.myflow.entity.core.MetaItem;

import java.util.Map;

public interface UIConverter {

    public boolean support(IMeta metaItem);

    public UiObject convert(IMeta metaItem);

    IEntity read(IMeta meta, Object map);
}
