package com.github.soonboylena.myflow.component.layout.converter;

import com.github.soonboylena.myflow.component.layout.ConverterManager;
import com.github.soonboylena.myflow.entity.core.*;
import com.github.soonboylena.myflow.support.UrlManager;
import com.github.soonboylena.myflow.vModel.UiContainer;
import com.github.soonboylena.myflow.vModel.UiObject;
import com.github.soonboylena.myflow.vModel.uiComponent.ListComponent;
import com.github.soonboylena.myflow.vModel.uiComponent.ListComponentDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 一览组件对应的converter
 */
public class ListConverter implements UIConverter {


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
    public UiObject meta2Page(IMeta meta, UiContainer container) {

        MetaList metaList = (MetaList) meta;

        Map<String, MetaField> metaPool = metaList.getMetaPool();

        ListComponent listComponent = new ListComponent();
        ListComponentDefine define = listComponent.getDefine();

        for (Map.Entry<String, MetaField> stringMetaFieldEntry : metaPool.entrySet()) {
//            String key = stringMetaFieldEntry.getKey();
            MetaField metaField = stringMetaFieldEntry.getValue();
            logger.debug("list组件列： key： {}, caption: {}", metaField.getKey(), metaField.getCaption());
            define.addColDefine(new ListComponentDefine.ColDefine(metaField.getKey(), metaField.getCaption(), metaField.isBusinessKey()));
        }
        define.setDataUrl(UrlManager.dataList(metaList.getKey()));

        if (container != null) {
            container.setCaption(metaList.getCaption());
            container.addContent(listComponent);
            return container;
        }


        return listComponent;
    }


    @Override
    public FormEntity pageData2Entity(IMeta meta, Object map) {
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
