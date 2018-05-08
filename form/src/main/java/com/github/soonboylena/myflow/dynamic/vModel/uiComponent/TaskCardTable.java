package com.github.soonboylena.myflow.dynamic.vModel.uiComponent;

import com.github.soonboylena.myflow.dynamic.vModel.AbstractDwc;
import com.github.soonboylena.myflow.dynamic.vModel.IUiDefinition;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Project Name:    spring-boot-admin
 * LoginInfoEntity:            sunb
 * Date:            2017-12-12
 * Time:            10:45
 */
public class TaskCardTable extends AbstractDwc<IUiDefinition> {

    private static final String TYPE = "mCardTable";

    @Override
    public String getType() {
        return TYPE;
    }
}
