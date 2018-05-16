package com.github.soonboylena.myflow.entity.core;


/**
 * IEntity: 一个实体，包括数据以及数据的定义
 */
public interface IEntity {

    public IMeta acquireMeta();

    public Object getData();

}
