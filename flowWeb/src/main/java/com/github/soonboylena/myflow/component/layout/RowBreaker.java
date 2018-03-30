package com.github.soonboylena.myflow.component.layout;

import com.github.soonboylena.myflow.entity.core.MetaForm;


public interface RowBreaker {

    /**
     * 判断是否要新开一行
     *
     * @param totalIndex 当前控件在当区域的index
     * @param cursor     当前控件在当前行的游标
     * @param span       当前控件所占宽度
     * @param colsInRow  一行最大放几个控件
     * @param metaForm   当前区域所有的控件
     * @return true:需要重起一行进行布局； false：不需要
     */
    boolean isBreakPoint(int totalIndex, int cursor, int span, int colsInRow, MetaForm metaForm);
}
