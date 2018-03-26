package com.github.soonboylena.activiti.component.layout;

import com.github.soonboylena.activiti.vModel.UiObject;
import com.github.soonboylena.activiti.vModel.uiComponent.Column;
import com.github.soonboylena.activiti.vModel.uiComponent.Row;
import com.github.soonboylena.activiti.vModel.uiComponent.Section;
import com.github.soonboylena.entity.core.MetaForm;
import com.github.soonboylena.entity.core.MetaItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class SimpleLayoutResolver implements LayoutResolver {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemConverters itemConverters;

    // 将unit个栅格作为一个单元处理
    private int unit;

    /**
     * 一行有几个组件
     */
    @Value("${layout.simple.rows:3}")
    private int colsInRow;


    //栅格布局宽度
    private static final int GRID_LAYOUT_COL_NUMBER = 24;

    private List<RowBreaker> breakers = new ArrayList<>();

    public SimpleLayoutResolver() {
//        breakers.add(new DateRowBreaker());
//        breakers.add(new SelectItemRowBreaker());
    }


    @Override
    public UiObject arrangeAsForm(MetaForm metaForm) {

        this.unit = GRID_LAYOUT_COL_NUMBER / colsInRow;
        Collection<MetaItem> fields = metaForm.getFields();

        Section s = new Section();
        if (fields == null || fields.isEmpty()) {
            return s;
        }
        // 整体的游标
        int totalIndex = 0;

        Row currentRow = null;
        // 行游标
        int cursor = 0;


        for (MetaItem metaField : fields) {

            // 设定每个项目宽度是1。 需要的话可以单独处理这个值
            int span = 1;

            // 如果游标处于0位 或者已经放不下了
            if (cursor == 0 || colsInRow - cursor < span || isBreakPoint(totalIndex, cursor, span, colsInRow, metaForm)) {
                cursor = 0;
                currentRow = new Row();
                s.addContent(currentRow);
            }

            currentRow.addContent(swapWithCol(metaField, cursor, span));

            //调整游标
            totalIndex += span;
            cursor++;
        }
        return s;
    }

    private boolean isBreakPoint(int totalIndex, int cursor, int span, int colsInRow, MetaForm metaForm) {

        for (RowBreaker breaker : breakers) {
            if (breaker.isBreakPoint(totalIndex, cursor, span, colsInRow, metaForm)) {
                return true;
            }
        }
        return false;
    }

    private UiObject swapWithCol(MetaItem metaField, int cursor, int span) {
        Column c = new Column(0, span * unit);
        c.addContent(itemConverters.convert(metaField));
        return c;
    }

}
