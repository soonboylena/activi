package com.github.soonboylena.myflow.dynamic.vModel;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Project Name:    spring-boot-admin
 * LoginInfoEntity:            sunb
 * Date:            2018-01-10
 * Time:            11:57
 */
@Data
@NoArgsConstructor
public class DataItem {

    private String key;
    private Object value;
    private String type;
//    private Map<String, String> _META_;

    public DataItem(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public DataItem(String key, Object value, String type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }
}
