package com.github.soonboylena.myflow.persistentneo4j.entity;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Properties;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@NodeEntity
@Getter
@Setter
public class DynamicEntity extends BaseModel {

    public DynamicEntity(String title, String key) {
        super(title);
        super.setLabels(Collections.singleton(key));
    }

    @Properties
    private Map<String, Object> properties = new HashMap<>();

    public void addProperty(String key, Object data) {
        properties.put(key, data);
    }
}
