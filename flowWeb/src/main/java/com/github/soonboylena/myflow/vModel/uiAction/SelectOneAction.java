package com.github.soonboylena.myflow.vModel.uiAction;

import com.github.soonboylena.myflow.vModel.contant.ClientActionType;
import com.github.soonboylena.myflow.vModel.uiComponent.Button;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Project Name:    spring-boot-admin
 * LoginInfoEntity:            sunb
 * Date:            2017-12-21
 * Time:            9:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SelectOneAction extends LinkAction {

    private final static ClientActionType type = ClientActionType.selectOne;

    private String message;
    private List<Button> buttons = new ArrayList<>(5);
}
