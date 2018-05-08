package com.github.soonboylena.myflow.dynamic.vModel.uiComponent;

import com.github.soonboylena.myflow.dynamic.vModel.AbstractDwc;
import com.github.soonboylena.myflow.dynamic.vModel.IUiDefinition;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Project Name:    spring-boot-admin
 * LoginInfoEntity:            sunb
 * Date:            2017-12-08
 * Time:            13:20
 */
public class Tabs extends AbstractDwc<TabsDefinition> {

    private static transient final String TYPE = "m-tabs";

    public static transient final String MODE_VERTICAL = "vertical";
    public static transient final String MODE_HORIZONTAL = "horizontal";

    public Tabs() {
        setDefine(new TabsDefinition());
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public void setMode(String mode) {
        getDefine().setMode(mode);
    }
}

@Data
class TabsDefinition implements IUiDefinition {
    private String mode = Tabs.MODE_HORIZONTAL;
}

