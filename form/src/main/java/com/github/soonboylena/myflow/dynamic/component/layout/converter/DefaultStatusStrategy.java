package com.github.soonboylena.myflow.dynamic.component.layout.converter;

import com.github.soonboylena.myflow.entity.core.IMeta;

/**
 * 默认的状态策略；这个策略是在配置里边写死的，不能随着状态变化而变化
 */
public class DefaultStatusStrategy implements StatusStrategy {

    @Override
    public boolean isReadonly(IMeta meta) {
        return meta.isReadonly();
    }

}
