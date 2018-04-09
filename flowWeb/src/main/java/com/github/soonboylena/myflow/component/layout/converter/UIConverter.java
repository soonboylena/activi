package com.github.soonboylena.myflow.component.layout.converter;

import com.github.soonboylena.myflow.entity.core.IEntity;
import com.github.soonboylena.myflow.entity.core.IMeta;
import com.github.soonboylena.myflow.vModel.UiObject;

/**
 * 转换器，用来转换画面UI组件与基础结构
 * support: 是否支持
 * convert：将IMeta转为画面UI组件
 * read：   把画面提交的数据整理，转成IEntity
 */
public interface UIConverter {

    public boolean support(IMeta metaItem);

    public UiObject convert(IMeta metaItem);

    public IEntity read(IMeta meta, Object data);
}
