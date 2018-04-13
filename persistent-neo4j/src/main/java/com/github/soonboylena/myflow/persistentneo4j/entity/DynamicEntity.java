package com.github.soonboylena.myflow.persistentneo4j.entity;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Properties;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@NodeEntity
@Getter
@Setter
public class DynamicEntity extends BaseModel {

    private transient static final Logger logger = LoggerFactory.getLogger(DynamicEntity.class);

    public DynamicEntity(String title, String key) {
        super(title);
        super.setLabels(Collections.singleton(key));
    }

    @Properties(allowCast = true)
    private Map<String, Object> properties = new HashMap<>();

    @Relationship(type = "hasRelation")
    private Set<DynamicRelation> relationShips = new HashSet<>();


    public void addProperty(String key, Object data) {
        if (data == null) {
            logger.warn("尝试将[{}]的property [{}] 为null。改处理将被忽略", getTitle(), key);
            return;
        }
        logger.trace("[{}]: \\{{}:{}\\}", getTitle(), key, data);
        properties.put(key, data);
    }


//    public void addRelation(DynamicRelation relation) {
//        this.relationShips.add(relation);
//    }

    public void addRelation(String type, DynamicEntity... relatedEntities) {
        if (relatedEntities != null) {
            for (DynamicEntity relatedEntity : relatedEntities) {
                DynamicRelation dynamicRelation = new DynamicRelation(this, relatedEntity, type);
                this.relationShips.add(dynamicRelation);
            }
        }
    }


//    public void addOther(String key, DynamicEntity other) {
//        this.others.put(key, other);
//    }
}
