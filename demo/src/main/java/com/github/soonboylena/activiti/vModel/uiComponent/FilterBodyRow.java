package com.github.soonboylena.activiti.vModel.uiComponent;

import com.github.soonboylena.activiti.vModel.IUiDefinition;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ZhaoPeng
 * @Date: 2017/10/31 13:39
 * @Description: 组件行
 */
@Data
public class FilterBodyRow implements IUiDefinition, Serializable {

    private String id ;

    // 行标题
    private String title;

    // 行设置
    private BodyOption option;

    // 行内btn
    private List<BodyNode> child;

    public FilterBodyRow () {
        this.child = new ArrayList<>();
    }

    public FilterBodyRow (Boolean multiSelect, Boolean isRegion, Boolean advancedSelect) {
        this();
        this.option = new BodyOption(multiSelect, isRegion, advancedSelect);
    }

    // 添加按钮
    public void addChild (String id, String title) {
        this.child.add(new BodyNode(id, title));
    }
}

/**
 * @Author: ZhaoPeng
 * @Date: 2017/10/31 13:40
 * @Description: 组件行内btn
 */
@Data
class BodyNode implements IUiDefinition {
    String id;
    String title;

    BodyNode (String id, String title) {
        this.id = id;
        this.title = title;
    }
}

/**
 * @Author: ZhaoPeng
 * @Date: 2017/10/31 13:40
 * @Description: 组件行option
 */
@Data
@NoArgsConstructor
class BodyOption implements IUiDefinition {
    Boolean multiSelect;
    Boolean isRegion;
    Boolean advancedSelect;

    public BodyOption (Boolean multiSelect, Boolean isRegion, Boolean advancedSelect) {
        this.multiSelect = multiSelect;
        this.isRegion = isRegion;
        this.advancedSelect = advancedSelect;
    }
}
