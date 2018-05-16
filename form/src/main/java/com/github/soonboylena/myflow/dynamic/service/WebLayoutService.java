package com.github.soonboylena.myflow.dynamic.service;

import com.github.soonboylena.myflow.dynamic.component.layout.ConverterManager;
import com.github.soonboylena.myflow.dynamic.support.KeyConflictCollection;
import com.github.soonboylena.myflow.dynamic.vModel.UiContainer;
import com.github.soonboylena.myflow.dynamic.vModel.UiObject;
import com.github.soonboylena.myflow.dynamic.vModel.uiComponent.Page;
import com.github.soonboylena.myflow.entity.config.ConfigureHolder;
import com.github.soonboylena.myflow.entity.core.FormEntity;
import com.github.soonboylena.myflow.entity.core.IEntity;
import com.github.soonboylena.myflow.entity.core.MetaForm;
import com.github.soonboylena.myflow.entity.core.MetaList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;


/**
 * 生成画面
 */
@Service
public class WebLayoutService {

    @Autowired
    private ConverterManager converterManager;

    @Autowired
    private ConfigureHolder holder;

    /**
     * 生成画面的layout
     *
     * @param formKey
     * @param container
     * @return
     */
    public UiContainer buildFormLayout(String formKey, UiContainer container) {
        MetaForm metaForm = holder.getMetaForm(formKey);
        Objects.requireNonNull(metaForm, "没有找到formKey: [ " + formKey + " ] 的定义.");
        return buildFormLayout(metaForm, container);
    }

    /**
     * 生成画面的layout
     *
     * @param metaForm
     * @param container
     * @return
     */
    public UiContainer buildFormLayout(MetaForm metaForm, UiContainer container) {
        container.setCaption(metaForm.getCaption());
        return (UiContainer) converterManager.meta2Page(metaForm, container);
    }

    /**
     * 生成画面的layout + 数据
     *
     * @param
     * @param
     * @return
     */
    public Page buildFormLayout(FormEntity formEntity) {
        Page page = new Page();
        MetaForm metaForm = formEntity.acquireMeta();
        buildFormLayout(metaForm, page);
        Map<String, Object> data = converterManager.entityData2PageMap(formEntity);
        page.setData(data);
        return page;
    }

    /**
     * 一览页面的layout
     *
     * @param formKey
     * @return
     */
    public UiObject listLayout(String formKey) {

        MetaForm metaForm = holder.getMetaForm(formKey);
        MetaList metaList = new MetaList(metaForm);

        Page page = new Page(metaForm.getCaption(), "一览");
        return converterManager.meta2Page(metaList, page);
    }
}
