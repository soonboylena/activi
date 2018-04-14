package com.github.soonboylena.myflow.vModel;

public abstract class AbstractContainerDefinition implements IContainerDefinition {

    private String caption;

    public String getCaption() {
        return caption;
    }

    @Override
    public void setCaption(String caption) {
        this.caption = caption;
    }
}
