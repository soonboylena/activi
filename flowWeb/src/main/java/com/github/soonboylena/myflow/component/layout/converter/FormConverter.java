package com.github.soonboylena.myflow.component.layout.converter;

import com.github.soonboylena.myflow.component.layout.ConverterManager;
import com.github.soonboylena.myflow.component.layout.RowBreaker;
import com.github.soonboylena.myflow.vModel.UiObject;
import com.github.soonboylena.myflow.vModel.uiComponent.Column;
import com.github.soonboylena.myflow.vModel.uiComponent.Form;
import com.github.soonboylena.myflow.vModel.uiComponent.Row;
import com.github.soonboylena.myflow.entity.core.*;

import java.util.*;

public class FormConverter implements UIConverter {

    private ConverterManager converterManager;

    // 将unit个栅格作为一个单元处理
    private int unit;

    /**
     * 一行有几个组件
     */
    private int colsInRow = 4;


    //栅格布局宽度
    private static final int GRID_LAYOUT_COL_NUMBER = 24;

    private List<RowBreaker> breakers = new ArrayList<>();


    public FormConverter(ConverterManager converterManager) {
        this.converterManager = converterManager;
    }

    @Override
    public boolean support(IMeta metaItem) {
        return metaItem instanceof MetaForm;
    }

    @Override
    public UiObject convert(IMeta meta) {

        MetaForm metaForm = (MetaForm) meta;

        this.unit = GRID_LAYOUT_COL_NUMBER / colsInRow;
        Collection<MetaField> fields = metaForm.getMetas();

        Form s = new Form(metaForm.getKey(), metaForm.getCaption());
        if (fields == null || fields.isEmpty()) {
            return s;
        }
        // 整体的游标
        int totalIndex = 0;

        Row currentRow = null;
        // 行游标
        int cursor = 0;


        for (MetaField metaField : fields) {

            // 设定每个项目宽度是1。 需要的话可以单独处理这个值
            int span = 1;

            // 如果游标处于0位 或者已经放不下了
            if (cursor == 0 || colsInRow - cursor < span || isBreakPoint(totalIndex, cursor, span, colsInRow, metaForm)) {
                cursor = 0;
                currentRow = new Row();
                s.addContent(currentRow);
            }

            // 如果是form的代表字段，强行设置为必须输入
            if (metaField.getKey().equalsIgnoreCase(metaForm.getBusinessKey())) {
                metaField.setRequired(true);
            }

            currentRow.addContent(swapWithCol(metaField, cursor, span));

            //调整游标
            totalIndex += span;
            cursor++;
        }
        return s;
    }

    @Override
    public FormEntity read(IMeta meta, Object map) {

        if (!(map instanceof Map)) {
            throw new IllegalArgumentException("类型不正确。类型需要是Map的子类, 传入的类型是 " + map.getClass().getSimpleName() + "");
        }

        Objects.requireNonNull(meta);
        MetaForm metaForm = (MetaForm) meta;
        Collection<MetaField> metas = metaForm.getMetas();

        Map<String, Object> _map = (Map<String, Object>) map;

        FormEntity formEntity = new FormEntity(metaForm);
        for (MetaField metaField : metas) {
            String key = metaField.getKey();
            Object o = _map.get(key);
            // 转成 IEntity ； 里边的meta信息不要
            IEntity read = converterManager.read(metaField, o);
            formEntity.addData(key, read.getData());
        }

        return formEntity;
    }

    private boolean isBreakPoint(int totalIndex, int cursor, int span, int colsInRow, MetaForm metaForm) {

        for (RowBreaker breaker : breakers) {
            if (breaker.isBreakPoint(totalIndex, cursor, span, colsInRow, metaForm)) {
                return true;
            }
        }
        return false;
    }

    private UiObject swapWithCol(MetaField metaField, int cursor, int span) {
        Column c = new Column(0, span * unit);
        c.addContent(converterManager.convert(metaField));
        return c;
    }

}
