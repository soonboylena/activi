package com.github.soonboylena.myflow.Auth.bean;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
*   @author lungern xiii.at.cn@gmail.com
*   @date 2018/2/2
*
*/
@Data
@RequiredArgsConstructor
public class MenuNode implements Serializable {


    private static final long serialVersionUID = -1466443749758657884L;
    @NonNull
    private Menu menu;

    private List<MenuNode> children;

    public void sortChildrenOrder() {
        if (null != children && children.size() > 1) {
            Collections.sort(children, (item1, item2) -> {
                item1.sortChildrenOrder();
                item2.sortChildrenOrder();
                if (item1.getMenu().getMenuOrder() > item2.getMenu().getMenuOrder()) {
                    return 1;
                } else if(item1.getMenu().getMenuOrder() == item2.getMenu().getMenuOrder()) {
                    return 0;
                }
                return -1;
            });
        }
    }

}
