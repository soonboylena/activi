package com.github.soonboylena.myflow.dynamic.component.layout.converter;//package com.github.soonboylena.myflow.component.layout.converter;
//
//import com.github.soonboylena.myflow.component.layout.ConverterManager;
//import com.github.soonboylena.myflow.component.layout.RowBreaker;
//import com.github.soonboylena.myflow.entity.core.*;
//import com.github.soonboylena.myflow.vModel.UiObject;
//import com.github.soonboylena.myflow.vModel.uiComponent.Column;
//import com.github.soonboylena.myflow.vModel.uiComponent.Form;
//import com.github.soonboylena.myflow.vModel.uiComponent.Page;
//
//import java.util.*;
//
//public class ViewConverter implements UIConverter {
//
//    private ConverterManager converterManager;
//
//
//    public ViewConverter(ConverterManager converterManager) {
//        this.converterManager = converterManager;
//    }
//
//    @Override
//    public boolean support(IMeta metaItem) {
//        return metaItem instanceof MetaView;
//    }
//
//    @Override
//    public UiObject meta2Page(IMeta meta) {
//
//        MetaView metaView = (MetaView) meta;
//        Page page = new Page(metaView.getCaption());
//        Collection<MetaForm> values = metaView.getMetaPool().values();
//        for (MetaForm value : values) {
//            Form form = (Form) converterManager.meta2Page(value);
//            page.addForm(form);
//        }
//        return page;
//    }
//
//    @Override
//    public ViewEntity pageData2Entity(IMeta meta, Object map) {
//
//        if (!(map instanceof Map)) {
//            throw new IllegalArgumentException("类型不正确。类型需要是Map的子类, 传入的类型是 " + map.getClass().getSimpleName() + "");
//        }
//
//        Objects.requireNonNull(meta);
//        MetaView metaView = (MetaView) meta;
//
//        ViewEntity entity = new ViewEntity(metaView);
//
//        Map<String, Map<String, Object>> data = (Map<String, Map<String, Object>>) map;
//
//        for (Map.Entry<String, Map<String, Object>> stringMapEntry : data.entrySet()) {
//
//            String key = stringMapEntry.getKey();
//            Map<String, Object> value = stringMapEntry.getValue();
//            FormEntity formEntiy = (FormEntity) converterManager.pageData2Entity(metaView.acquireMeta(key), value);
//            entity.addDatum(key, formEntiy.getData());
//        }
//        return entity;
//    }
//}
