package com.github.soonboylena.myflow.framework.web;

import com.github.soonboylena.myflow.entity.core.FormEntity;
import com.github.soonboylena.myflow.entity.core.ListEntity;
import com.github.soonboylena.myflow.entity.core.MetaForm;

/**
 * 持久层用的, 查询接口
 * meta + id ,返回 entity
 * 根据定义取数据
 */
public interface FormQueryService {

    FormEntity findById(MetaForm metaForm, Long id);

    ListEntity findByMeta(MetaForm metaForm);
}
