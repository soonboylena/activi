package com.github.soonboylena.myflow.dynamic.component.layout.converter;

import com.github.soonboylena.myflow.dynamic.vModel.UiContainer;
import com.github.soonboylena.myflow.dynamic.vModel.UiObject;
import com.github.soonboylena.myflow.entity.core.IEntity;
import com.github.soonboylena.myflow.entity.core.IMeta;

import java.util.Map;

/**
 * 转换器，用来转换画面UI组件与基础结构
 * support: 是否支持
 * meta2Page：将IMeta转为画面UI组件
 * pageData2Entity：   把画面提交的数据整理，转成IEntity
 * 泛型： PD:    PageData, convert接受这个类型的输入，可以构造内部真实的类型
 */
public interface UIConverter<PD, TD> {

    DefaultStatusStrategy DEFAULT_STATUS_STRATEGY = new DefaultStatusStrategy();

    public boolean support(IMeta metaItem);

    /**
     * 定义-->画面
     * 采用默认状态策略；
     *
     * @param metaItem  定义类型
     * @param container 指定的父容器
     * @return 如果指定了父容器，返回父容器；如果没有指定，由实现类自己处理返回UiObject;
     */
    default public UiObject meta2Page(IMeta metaItem, UiContainer container) {
        return meta2Page(metaItem, container, DEFAULT_STATUS_STRATEGY);
    }

    public UiObject meta2Page(IMeta meta, UiContainer container, StatusStrategy strategy);

    // 用meta和data构造IEntity
    public IEntity<TD> pageData2Entity(IMeta meta, PD data);

    // 从IEntity里边取数据,放到Map里边给画面用
    public void entityData2PageMap(IEntity<TD> entity, Map topMap);
}
