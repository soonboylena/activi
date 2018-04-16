package com.github.soonboylena.myflow.vModel.uiComponent;import com.fasterxml.jackson.annotation.JsonProperty;import com.github.soonboylena.myflow.vModel.IUiDefinition;import com.github.soonboylena.myflow.vModel.contant.ButtonType;import com.github.soonboylena.myflow.vModel.contant.SimpleListRowSelectType;import com.github.soonboylena.myflow.vModel.uiAction.LinkAction;import com.github.soonboylena.myflow.vModel.uiAction.UrlObject;import lombok.AllArgsConstructor;import lombok.Data;import java.util.ArrayList;import java.util.List;import java.util.Objects;/** * Created with IntelliJ IDEA. * Description: * Project Name:    spring-boot-admin * LoginInfoEntity:            sunb * Date:            2017-10-10 * Time:            17:38 */@Datapublic class ListComponentDefine implements IUiDefinition {    // 列表名称    private String text;    // 数据url    private UrlObject dataUrl;    // 明细数据里边的按钮    @JsonProperty(value = "showModalBtn")    private boolean hasRowButton = false;    // 头部按钮    @JsonProperty(value = "operation")    private List<Button> actions;    // 列定义    @JsonProperty(value = "cols")    private List<ColDefine> colDefines;    // 表格宽度    @JsonProperty(value = "tableWidth")    private String tableWidth;    // 单选/多选/不选    private SimpleListRowSelectType checkRow;    public void addColDefine(ColDefine colDefine) {        if (colDefines == null) colDefines = new ArrayList<>();        colDefines.add(colDefine);    }    public void addOperation(Button action) {        if (actions == null) this.actions = new ArrayList<>();        actions.add(action);    }    public void addOperation(List<Button> actions) {        if (this.actions == null) this.actions = new ArrayList<>();        this.actions.addAll(actions);    }    public void addAction(Button button) {        if (button == null) {            return;        }        if (this.actions == null) actions = new ArrayList<>();        actions.add(button);    }    /**     * 添加删除按钮     *     * @param urlObject     */    public void setDeleteUrl(UrlObject urlObject) {        Objects.requireNonNull(urlObject);        LinkAction action = new LinkAction();//        action.setType(ClientActionType.serverAction);        action.setUrl(urlObject);        action.setCallbackMethodName("reloadData");        Button button = new Button("删除", action);        button.setType(ButtonType.error);        addOperation(button);        // 设置单选        setCheckRow(SimpleListRowSelectType.M);    }    @Data    @AllArgsConstructor    public static class ColDefine {        private String key;        private String title;        private boolean link;        private String icon;        private String sort;        public ColDefine(String key, String title) {            this.key = key;            this.title = title;        }        public ColDefine(String key, String title, boolean isLink) {            this(key, title, isLink, null, null);        }    }}