package com.github.soonboylena.myflow.entity.config.builder;

import com.github.soonboylena.myflow.entity.config.ConfigureHolder;
import com.github.soonboylena.myflow.entity.core.AbstractMetaItem;
import com.github.soonboylena.myflow.entity.core.IMetaInput;

/**
 * 输入项目的Builder
 */
public interface InputItemBuilder {

    /**
     * @param config 待处理的当前结点
     * @param holder 正在处理的holder
     * @param source 整体来源
     * @return
     */
    public IMetaInput read(Object config, ConfigureHolder holder, Object source);

    public boolean support(Object config, ConfigureHolder holder, Object source);
}
