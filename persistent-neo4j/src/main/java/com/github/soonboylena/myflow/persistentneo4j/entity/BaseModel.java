package com.github.soonboylena.myflow.persistentneo4j.entity;

import org.neo4j.ogm.annotation.GraphId;

import java.io.Serializable;

/**
 * @author lungern xiii.at.cn@gmail.com
 * @date 2018/2/2
 */
public abstract class BaseModel implements Serializable {


    @GraphId
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
