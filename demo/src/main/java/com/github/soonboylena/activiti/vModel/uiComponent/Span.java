package com.github.soonboylena.activiti.vModel.uiComponent;

import com.github.soonboylena.activiti.vModel.AbstractDwc;
import com.github.soonboylena.activiti.vModel.IUiDefinition;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Project Name:    spring-boot-admin
 * UserEntity:            sunb
 * Date:            2017-12-04
 * Time:            13:35
 */
public class Span extends AbstractDwc<Define> {

    private transient static final String TYPE = "m-span";

    public Span() {
        setDefine(new Define());
    }

    public Span(String text) {
        this();
        getDefine().setText(text);
    }

    @Override
    public String getType() {
        return TYPE;
    }
}

@Data
class Define implements IUiDefinition {
    private String text;
}
