package com.github.soonboylena.activiti.vModel.uiAction;

import com.github.soonboylena.activiti.vModel.contant.ClientActionType;
import com.github.soonboylena.activiti.vModel.contant.ClientRouterMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author irvin
 * @date Create in 下午7:50 2017/11/2
 * @description 控件事件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientAction {
    private ClientActionType type = ClientActionType.link;
    private UrlObject url;
    private String at;
    private String confirm;
    private String alert;
    private ClientRouterMode mode;
    private String callbackMethodName;
    private String form = "form";

    public ClientAction(UrlObject url) {
        this.url = url;
    }

    public ClientAction(ClientActionType type) {
        this.type = type;
    }

    public ClientAction(ClientActionType type, UrlObject url) {
        this(type, url, null, null, null, null, null, null);
    }

    public ClientAction(ClientActionType type, UrlObject url, String at, String confirm, String alert, ClientRouterMode mode, String callbackMethodName) {
        this.type = type;
        this.url = url;
        this.at = at;
        this.confirm = confirm;
        this.alert = alert;
        this.mode = mode;
        this.callbackMethodName = callbackMethodName;
    }

    public void setUrl(String urlString) {
        this.url = new UrlObject(urlString);
    }

    public void setUrl(UrlObject urlObject) {
        this.url = urlObject;
    }

    /**
     * 返回一个错误信息
     *
     * @param message
     * @return
     */
    public static ClientAction errorMessageAction(String message) {
        ClientAction action = new ClientAction();
        action.setType(ClientActionType.message);
        action.setAlert(message);
        return action;
    }

    /**
     * 返回一个成功信息
     *
     * @param message
     * @return
     */
    public static ClientAction successMessageAction(String message) {
        ClientAction action = new ClientAction();
        action.setType(ClientActionType.message);
        action.setAlert(message);
        return action;
    }
}
