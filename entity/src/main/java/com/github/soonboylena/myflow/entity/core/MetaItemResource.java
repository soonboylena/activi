package com.github.soonboylena.myflow.entity.core;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 选择一个资源
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class MetaItemResource extends AbstractMetaItem {

    private String key;
    private String relationName;

    @Override
    public MetaInputType getType() {
        return MetaInputType.resource;
    }

    /**
     * 设定关系；如果为空就用key来替代
     * @param relationName
     */
    public void setRelationName(String relationName) {
        if (relationName == null || relationName.equals("")) {
            this.relationName = key;
        } else {
            this.relationName = relationName;
        }

    }
}
