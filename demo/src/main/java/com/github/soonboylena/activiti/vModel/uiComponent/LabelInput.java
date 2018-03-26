package com.github.soonboylena.activiti.vModel.uiComponent;import com.github.soonboylena.activiti.vModel.AbstractDwc;import com.github.soonboylena.activiti.vModel.IUiDefinition;import lombok.Data;/** * Created with IntelliJ IDEA. * Description: * Project Name:    spring-boot-admin * UserEntity:            sunb * Date:            2017-10-24 * Time:            16:10 */public class LabelInput extends AbstractDwc<LabelInputDefinition> implements UiItem {    private transient static final String TYPE = "m-labelInput";    public LabelInput(String caption, int labelWidth) {        LabelInputDefinition labelInputDefinition = new LabelInputDefinition();        labelInputDefinition.setLabel(caption);        labelInputDefinition.setLabelWidth(labelWidth);        setDefine(labelInputDefinition);    }    @Override    public String getType() {        return TYPE;    }}@Dataclass LabelInputDefinition implements IUiDefinition {    // label文字    String label;    // 是否显示    boolean hidden;    // label宽度    int labelWidth;}