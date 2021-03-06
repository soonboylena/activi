package com.github.soonboylena.myflow.dynamic.vModel.uiAction;

import com.github.soonboylena.myflow.dynamic.vModel.contant.ClientActionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SubmitAction extends AbstractAction {

    public SubmitAction(UrlObject url) {
        this.url = url;
    }

    @Override
    public ClientActionType getType() {
        return ClientActionType.submit;
    }
}
