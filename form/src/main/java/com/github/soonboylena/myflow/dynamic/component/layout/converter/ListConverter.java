package com.github.soonboylena.myflow.dynamic.component.layout.converter;

import com.github.soonboylena.myflow.dynamic.component.layout.ConverterManager;
import com.github.soonboylena.myflow.dynamic.vModel.UiContainer;
import com.github.soonboylena.myflow.dynamic.vModel.UiObject;
import com.github.soonboylena.myflow.dynamic.vModel.uiComponent.ListComponent;
import com.github.soonboylena.myflow.dynamic.vModel.uiComponent.ListComponentDefine;
import com.github.soonboylena.myflow.entity.core.*;
import com.github.soonboylena.myflow.dynamic.support.UrlManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 一览组件对应的converter
 */
public class ListConverter implements UIConverter<List, List> {


    private static Logger logger = LoggerFactory.getLogger(ListConverter.class);

    private ConverterManager converter;


    public ListConverter(ConverterManager converterManager) {
        this.converter = converterManager;
    }


    @Override
    public boolean support(IMeta metaItem) {
        return metaItem instanceof MetaList;
    }

    @Override
    public UiObject meta2Page(IMeta meta, UiContainer container, StatusStrategy statusStrategy) {

        MetaList metaList = (MetaList) meta;

        Map<String, MetaField> metaPool = metaList.getMetaPool();

        ListComponent listComponent = new ListComponent();
        ListComponentDefine define = listComponent.getDefine();

        for (Map.Entry<String, MetaField> stringMetaFieldEntry : metaPool.entrySet()) {
//            String key = stringMetaFieldEntry.getKey();
            MetaField metaField = stringMetaFieldEntry.getValue();
            logger.debug("list组件列： key： {}, caption: {}", metaField.getKey(), metaField.getCaption());

            define.addColDefine(metaField.getMetaItem());
        }
        define.setDataUrl(UrlManager.dataList(metaList.getKey()));
        define.setDetailUrl(UrlManager.pageInit(metaList.getKey()));

        if (metaList.getBusinessKey() == null || metaList.getBusinessKey().isEmpty()) {
            MetaItemString idCol = new MetaItemString();
            idCol.setKey("id");
            define.addColDefine(idCol);
            define.setBusinessKey("id");
        } else {
            define.setBusinessKey(metaList.getBusinessKey());
        }


        if (container != null) {
            container.setCaption(metaList.getCaption());
            container.addContent(listComponent);
            return container;
        }


        return listComponent;
    }


    @Override
    public ListEntity pageData2Entity(IMeta meta, List map) {
        return null;
    }

    @Override
    public void entityData2PageMap(IEntity entity, Map topMap) {

        ListEntity listEntity = (ListEntity) entity;
        List<FormEntity> forms = listEntity.getFormEntities();
        for (FormEntity form : forms) {
            converter.entityData2PageMap(form, topMap);
        }
    }

}
