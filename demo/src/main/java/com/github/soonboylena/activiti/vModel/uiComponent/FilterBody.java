package com.github.soonboylena.activiti.vModel.uiComponent;import com.github.soonboylena.activiti.vModel.UiObject;import lombok.Data;import java.util.ArrayList;import java.util.List;/** * @Author: zhao peng * @Date: 2017/10/31 * @Time: 9:30 * @Description: 条件过滤组件 */@Datapublic class FilterBody implements UiObject {    private String id = "m-filterBody";    private List<FilterBodyRow> rows;    public FilterBody() {        this.rows = new ArrayList<>();    }    public FilterBodyRow addRow(Boolean multiSelect, Boolean isRegion, Boolean advancedSelect) {        FilterBodyRow row = new FilterBodyRow(multiSelect, isRegion, advancedSelect);        this.rows.add(row);        return row;    }}