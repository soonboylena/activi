package com.github.soonboylena.myflow.persistentneo4j.entity;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Properties;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

@NodeEntity
@Getter
@Setter
public class DynamicEntity extends BaseModel {

    private transient static final Logger logger = LoggerFactory.getLogger(DynamicEntity.class);

    public DynamicEntity() {
    }

    public DynamicEntity(String title, String key) {
        super(title);
        super.setLabels(Collections.singleton(key));
    }

    @Properties(allowCast = true)
    private Map<String, Object> properties = new HashMap<>();

    @Relationship(type = "relateTo")
    private Set<DynamicRelation> relateTo = new HashSet<>();

    @Relationship(type = "relatedBy", direction = Relationship.INCOMING)
    private Set<DynamicRelation> relatedBy = new HashSet<>();

    public void addProperty(String key, Object data) {
        if (data == null) {
            logger.warn("尝试将[{}]的property [{}] 为null。该处理将被忽略", getTitle(), key);
            return;
        }
        logger.trace("[{}]: \\{{}:{}\\}", getTitle(), key, data);
        properties.put(key, data);
    }


    public void addRelation(String type, DynamicEntity... relatedEntities) {
        if (relatedEntities != null) {
            for (DynamicEntity relatedEntity : relatedEntities) {
                DynamicRelation dynamicRelation = new DynamicRelation(this, relatedEntity, type);
                this.relateTo.add(dynamicRelation);
                relatedEntity.relatedBy(dynamicRelation);
            }
        }
    }

    public Object getProperty(String key) {
        Object o = properties.get(key);
        return o;
    }

    /**
     * 根据type找所有联系并返回
     *
     * @param type
     * @return
     */
    public List<DynamicRelation> getRelationTo(String type) {
        return relateTo.stream().filter(s -> s.getType().equalsIgnoreCase(type)).collect(Collectors.toList());
    }

    public Set<DynamicRelation> getRelationTo() {
        return relateTo;
    }

    public void setRelationTo(Set<DynamicRelation> relationShips) {
        this.relateTo = relationShips;
    }

    public void relatedBy(DynamicRelation relation) {
        this.relatedBy.add(relation);
    }
}
