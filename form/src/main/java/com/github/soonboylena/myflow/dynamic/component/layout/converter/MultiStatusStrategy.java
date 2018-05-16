package com.github.soonboylena.myflow.dynamic.component.layout.converter;

import com.github.soonboylena.myflow.entity.core.IMeta;

/**
 * 最简单的多状态的策略
 * 只要多个里边有一个说是只读，就是只读
 */
public class MultiStatusStrategy implements StatusStrategy {

    protected StatusStrategy[] strategyList;

    public MultiStatusStrategy(StatusStrategy... strategyList) {
        this.strategyList = strategyList;
    }

    @Override
    public boolean isReadonly(IMeta metaField) {
        for (StatusStrategy statusStrategy : this.strategyList) {
            if (statusStrategy.isReadonly(metaField)) {
                return true;
            }
        }
        return false;
    }
}
