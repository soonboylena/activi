package com.github.soonboylena.activiti.support;

import java.util.HashMap;
import java.util.Map;

/**
 * 提供一个简化map的操作类，通过链式调用组装一个map。
 * 当
 */
public class ChainMap {

    private Map<String, Object> map = new HashMap<>();

    public static ChainMap get() {
        return new ChainMap();
    }

    private ChainMap() {
    }

    public ChainMap put(String key, Object object) {
        map.put(key, object);
        return this;
    }

    public Map<String, Object> ok() {
        return map;
    }

}
