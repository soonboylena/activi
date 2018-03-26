package com.github.soonboylena.activiti.vModel.uiComponent;

import com.github.soonboylena.activiti.vModel.AbstractDwc;
import com.github.soonboylena.activiti.vModel.IUiDefinition;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Project Name:    spring-boot-admin
 * UserEntity:            sunb
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
