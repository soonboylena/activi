package com.github.soonboylena.myflow.dynamic.vModel;

import com.github.soonboylena.myflow.dynamic.vModel.uiAction.UrlObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author irvin
 * @date Create in 下午7:50 2017/11/2
 * @description 控件事件
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Action {
    String type;
    String action;
    String eventId;
    String targetId;
    UrlObject link;
    Source source;
}
