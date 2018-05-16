package com.github.soonboylena.myflow.dynamic.component.layout.converter;

import com.github.soonboylena.myflow.entity.core.IMeta;

public class ReadonlyStrategy implements StatusStrategy {

    @Override
    public boolean isReadonly(IMeta metaField) {
        return true;
    }
}
