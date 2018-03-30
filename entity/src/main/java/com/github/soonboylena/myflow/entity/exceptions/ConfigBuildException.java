package com.github.soonboylena.myflow.entity.exceptions;

public class ConfigBuildException extends RuntimeException {

    public ConfigBuildException(Exception message) {
        super(message);
    }

    public ConfigBuildException(String s) {
        super(s);
    }
}
