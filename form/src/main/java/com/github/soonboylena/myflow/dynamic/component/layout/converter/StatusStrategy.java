package com.github.soonboylena.myflow.dynamic.component.layout.converter;

import com.github.soonboylena.myflow.entity.core.MetaField;

public interface StatusStrategy {

    public boolean isReadonly(MetaField metaField);

}
