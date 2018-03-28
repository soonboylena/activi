package com.github.soonboylena.activiti.vModel.uiAction;

import com.github.soonboylena.activiti.vModel.contant.ClientActionType;
import com.github.soonboylena.activiti.vModel.contant.ClientRouterMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author irvin
 * @date Create in 下午7:50 2017/11/2
 * @description 控件事件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LinkAction extends AbstractAction {
    private String at;
    private String confirm;
    private String alert;
    private ClientRouterMode mode;
    private String callbackMethodName;
    private String forms;

    public LinkAction(UrlObject url) {
        this.url = url;
    }


    public LinkAction(ClientActionType type, UrlObject url, String at, String confirm, String alert, ClientRouterMode mode, String callbackMethodName) {
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

    @Override
    public ClientActionType getType() {
        return ClientActionType.link;
    }


}
