package com.github.soonboylena.myflow.dynamic.vModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author irvin
 * @date Create in 下午8:11 2017/11/2
 * @description 事件触发源
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Source {
    String id;//组件 id
    String type;//组件类型
}
