package com.github.soonboylena.myflow.dynamic.vModel.contant;

public enum DispatchActionType {

    link("link"),
    create("create"),
    submit("submit"),
    delete("delete"),
    serverAction("serverAction");

    private String text;

    DispatchActionType(String text) {
        this.text = text;
    }

    public String text() {
        return text;
    }

}
