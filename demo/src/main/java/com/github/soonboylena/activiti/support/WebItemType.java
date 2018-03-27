package com.github.soonboylena.activiti.support;

public enum WebItemType {

    StringType("string", "mInput");

    private String literal;
    private String webType;

    WebItemType(String literal, String webType) {
        this.literal = literal;
        this.webType = webType;
    }

    public String literal() {
        return this.literal;
    }

    public String webType() {
        return this.webType;
    }

    public static WebItemType get(String literal) {
        for (WebItemType webItemType : WebItemType.values()) {
            if (webItemType.literal.equalsIgnoreCase(literal)) {
                return webItemType;
            }
        }
        throw new IllegalArgumentException("can not convert string \"" + literal + "\" to WebItemType ");
    }
}
