package com.github.soonboylena.myflow.component.layout.converter;

import com.github.soonboylena.myflow.entity.core.*;
import com.github.soonboylena.myflow.vModel.UiContainer;
import com.github.soonboylena.myflow.vModel.UiObject;
import com.github.soonboylena.myflow.vModel.uiComponent.ListComponent;
import com.github.soonboylena.myflow.vModel.uiComponent.ListComponentDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 一览组件对应的converter
 */
public class ListConverter implements UIConverter {


    private static Logger logger = LoggerFactory.getLogger(ListConverter.class);

//    private ConverterManager converterManager;


//    public ListConverter(ConverterManager converterManager) {
//        this.converterManager = converterManager;
//    }


    @Override
    public boolean support(IMeta metaItem) {
        return metaItem instanceof MetaList;
    }

    @Override
    public UiObject convert(IMeta meta, UiContainer container) {

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

        if (container != null) {
            container.setCaption(metaList.getCaption());
            container.addContent(listComponent);
            return container;
        }

        return listComponent;
    }


    @Override
    public FormEntity read(IMeta meta, Object map) {
        return null;
    }

    @Override
    public void loadData(IEntity entity, Map topMap) {
    }

}
