package com.github.soonboylena.myflow.vModel.uiAction;

import com.github.soonboylena.myflow.vModel.contant.ClientActionType;

public class MessageAction extends AbstractAction {

    private final static ClientActionType type = ClientActionType.message;

    private String alert;

    @Override
    public ClientActionType getType() {
        return type;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public static MessageAction error(String alert) {
        MessageAction action = new MessageAction();
        action.setAlert(alert);
        return action;
    }
}
