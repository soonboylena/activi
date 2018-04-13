package com.github.soonboylena.myflow.persistentneo4j.entity;

import org.neo4j.ogm.annotation.Labels;
import org.neo4j.ogm.annotation.NodeEntity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author lungern xiii.at.cn@gmail.com
 * @date 2018/2/2
 */
public abstract class BaseModel implements Serializable {

    private Long id;

    private String title;

    @Labels
    private Set<String> labels = new HashSet<>();

    public BaseModel() {
    }

    public BaseModel(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<String> getLabels() {
        return labels;
    }

    public void setLabels(Set<String> labels) {
        this.labels = labels;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
