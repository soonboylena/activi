package com.github.soonboylena.myflow.component.layout.converter;

import com.github.soonboylena.myflow.component.layout.ConverterManager;
import com.github.soonboylena.myflow.component.layout.RowBreaker;
import com.github.soonboylena.myflow.entity.core.*;
import com.github.soonboylena.myflow.vModel.UiContainer;
import com.github.soonboylena.myflow.vModel.UiObject;
import com.github.soonboylena.myflow.vModel.uiComponent.Column;
import com.github.soonboylena.myflow.vModel.uiComponent.Form;
import com.github.soonboylena.myflow.vModel.uiComponent.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class FormConverter implements UIConverter {


    private static Logger logger = LoggerFactory.getLogger(FormConverter.class);

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
    public UiObject convert(IMeta meta, UiContainer container) {

        MetaForm metaForm = (MetaForm) meta;

        this.unit = GRID_LAYOUT_COL_NUMBER / colsInRow;
        Collection<MetaField> fields = metaForm.getMetas();

        Form s = new Form(metaForm.getKey(), metaForm.getCaption());
        container.addContent(s);
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

        // 处理关系
        for (MetaForm.Relation relation : metaForm.getRelations()) {
            MetaForm relatedForm = relation.getRelatedForm();
            // 递归，把有关联的form装到容器里边； 这个过程中嵌套关系会变成Array

            logger.debug("嵌套form： type：{}， 下个form caption:{}", relation.getType(), relatedForm.getCaption());
            this.convert(relatedForm, container);
        }

        return container;
    }

    @Override
    public FormEntity read(IMeta meta, Object map) {

        if (!(map instanceof Map)) {
            throw new IllegalArgumentException("类型不正确。类型需要是Map的子类, 传入的类型是 " + map.getClass().getSimpleName() + "");
        }

        Objects.requireNonNull(meta);
        MetaForm metaForm = (MetaForm) meta;
        Collection<MetaField> metas = metaForm.getMetas();

        Map<String, Object> _map = ((Map<String, Map<String, Object>>) map).get(meta.getKey());

        FormEntity formEntity = new FormEntity(metaForm);
        for (MetaField metaField : metas) {
            String key = metaField.getKey();
            Object o = _map.get(key);
            // 转成 IEntity ； 里边的meta信息不要
            IEntity read = converterManager.read(metaField, o);
            formEntity.addData(key, read.getData());
        }

        List<MetaForm.Relation> relations = metaForm.getRelations();
        for (MetaForm.Relation relation : relations) {
            MetaForm relatedForm = relation.getRelatedForm();
            String key = relatedForm.getKey();
            // 提前判断一下是否有这个下级form对应的数据；没有的话就不建了
            if (!((Map) map).containsKey(key)) {
                continue;
            }
            FormEntity nextFormEntity = read(relatedForm, map);
            formEntity.addRelatedForm(relation.getType(), nextFormEntity);
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
        UiObject convert = converterManager.convert(metaField, c);
        return c;
    }

}
