package com.github.soonboylena.myflow.vModel.uiComponent;

import com.github.soonboylena.myflow.vModel.AbstractDwc;
import com.github.soonboylena.myflow.vModel.IUiDefinition;
import com.github.soonboylena.myflow.vModel.uiAction.UrlObject;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Project Name:    spring-boot-admin
 * LoginInfoEntity:            sunb
 * Date:            2017-12-08
 * Time:            13:47
 */
public class UrlSection extends AbstractDwc<UrlSectionDefinition> {

    private transient static final String TYPE = "m-url-section";

    public UrlSection(UrlObject defineUrl) {
        this(defineUrl, null);
    }

    public UrlSection(UrlObject defineUrl, UrlObject dataUrl) {
        setDefine(new UrlSectionDefinition());
        getDefine().setDefineUrl(defineUrl);
        if (dataUrl != null) getDefine().setDataUrl(dataUrl);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public void setDataUrl(UrlObject urlObject) {
        getDefine().setDataUrl(urlObject);
    }

//    public UrlObject getDefineUrl() {
//        return getDefine().getDefineUrl();
//    }
}

@Data
class UrlSectionDefinition implements IUiDefinition {
    private UrlObject defineUrl;
    private UrlObject dataUrl;
    private UrlObject statusUrl;
}
