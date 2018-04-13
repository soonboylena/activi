package com.github.soonboylena.myflow.component.layout.converter;

import com.github.soonboylena.myflow.component.layout.FormRawData;
import com.github.soonboylena.myflow.entity.core.IEntity;
import com.github.soonboylena.myflow.entity.core.IMeta;
import com.github.soonboylena.myflow.vModel.UiContainer;
import com.github.soonboylena.myflow.vModel.UiObject;

import java.util.Map;

/**
 * 转换器，用来转换画面UI组件与基础结构
 * support: 是否支持
 * convert：将IMeta转为画面UI组件
 * read：   把画面提交的数据整理，转成IEntity
 */
public interface UIConverter {

    public boolean support(IMeta metaItem);

    /**
     * 定义-->画面
     *
     * @param metaItem  定义类型
     * @param container 指定的父容器
     * @return 如果指定了父容器，返回父容器；如果没有指定，由实现类自己处理返回UiObject;
     */
    public UiObject convert(IMeta metaItem, UiContainer container);

    public IEntity read(IMeta meta,  Object data);
}
