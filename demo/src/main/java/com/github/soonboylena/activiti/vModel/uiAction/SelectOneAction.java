package com.github.soonboylena.activiti.vModel.uiAction;

import com.github.soonboylena.activiti.vModel.contant.ClientActionType;
import com.github.soonboylena.activiti.vModel.uiComponent.Button;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Project Name:    spring-boot-admin
 * UserEntity:            sunb
 * Date:            2017-12-21
 * Time:            9:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SelectOneAction extends LinkAction {

    private ClientActionType type = ClientActionType.selectOne;
    private String message;
    private List<Button> buttons = new ArrayList<>(5);
}
