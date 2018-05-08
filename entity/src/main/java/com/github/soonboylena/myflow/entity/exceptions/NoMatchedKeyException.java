package com.github.soonboylena.myflow.entity.exceptions;

public class NoMatchedKeyException extends RuntimeException {

    public NoMatchedKeyException(String key, String type) {
        super(String.format("配置文件中，没有找到对应[%s]的[%s]", key, type));
    }
}
