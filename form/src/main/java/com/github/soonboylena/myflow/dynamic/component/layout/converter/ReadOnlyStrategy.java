package com.github.soonboylena.myflow.dynamic.component.layout.converter;

import com.github.soonboylena.myflow.entity.core.MetaField;

public class ReadOnlyStrategy implements StatusStrategy {

    @Override
    public boolean isReadonly(MetaField metaField) {
        return true;
    }
}
