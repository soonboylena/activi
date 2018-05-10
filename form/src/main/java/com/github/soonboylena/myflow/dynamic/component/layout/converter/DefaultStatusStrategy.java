package com.github.soonboylena.myflow.dynamic.component.layout.converter;

import com.github.soonboylena.myflow.entity.core.MetaField;

public class DefaultStatusStrategy implements StatusStrategy {

    @Override
    public boolean isReadonly(MetaField meta) {
        return meta.isReadOnly();
    }

}
