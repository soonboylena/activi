package com.github.soonboylena.myflow.dynamic.vModel.uiAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: ZhaoPeng
 * @Date: 2017/11/23 11:55
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParamsRelation<T> {

    // 前台用来区分是需要从vuex中取值还是直接替换
    private final boolean getVuexValue = true;
    // vuex中的key值 ['selectId', 'selectValue', 'id']
    private List<String> value;
    private T defaultValue;
}
