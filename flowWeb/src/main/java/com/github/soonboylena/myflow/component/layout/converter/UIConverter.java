package com.github.soonboylena.myflow.component.layout.converter;

import com.github.soonboylena.myflow.vModel.UiObject;
import com.github.soonboylena.myflow.entity.core.IEntity;
import com.github.soonboylena.myflow.entity.core.IMeta;

public interface UIConverter {

    public boolean support(IMeta metaItem);

    public UiObject convert(IMeta metaItem);

    public IEntity read(IMeta meta, Object data);
}
