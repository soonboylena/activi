package com.github.soonboylena.myflow.dynamic.vModel.uiComponent;import com.github.soonboylena.myflow.dynamic.vModel.AbstractContainer;import com.github.soonboylena.myflow.dynamic.vModel.AbstractContainerDefinition;import lombok.Data;/** * Created with IntelliJ IDEA. * Description: * Project Name:    spring-boot-admin * LoginInfoEntity:            sunb * Date:            2017-10-24 * Time:            16:10 */public class LabelInput extends AbstractContainer<LabelInputDefinition> implements UiItem {    private transient static final String TYPE = "m-labelInput";    public LabelInput(String caption, int labelWidth) {        LabelInputDefinition labelInputDefinition = new LabelInputDefinition();        labelInputDefinition.setLabel(caption);        labelInputDefinition.setLabelWidth(labelWidth);        setDefine(labelInputDefinition);    }    @Override    public String getType() {        return TYPE;    }}@Dataclass LabelInputDefinition extends AbstractContainerDefinition {    // label文字    String label;    // 是否显示    boolean hidden;    // label宽度    int labelWidth;}