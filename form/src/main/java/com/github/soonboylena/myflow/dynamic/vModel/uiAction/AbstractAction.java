package com.github.soonboylena.myflow.dynamic.vModel.uiAction;

import com.github.soonboylena.myflow.dynamic.vModel.contant.ClientActionType;
import lombok.Data;

@Data
public abstract class AbstractAction {

    protected UrlObject url;

    public UrlObject getUrl() {
        return url;
    }

    public void setUrl(UrlObject url) {
        this.url = url;
    }

    public abstract ClientActionType getType();
}
