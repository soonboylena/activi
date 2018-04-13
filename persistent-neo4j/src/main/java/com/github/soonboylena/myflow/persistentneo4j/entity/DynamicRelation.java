package com.github.soonboylena.myflow.persistentneo4j.entity;

import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "hasRelation")
@Data
public class DynamicRelation {

    private Long id;

    @StartNode
    DynamicEntity start;

    @EndNode
    DynamicEntity end;

    public DynamicRelation() {
    }

    public DynamicRelation(DynamicEntity start, DynamicEntity end, String type) {
        this.start = start;
        this.end = end;
        this.type = type;
    }

    @Property
    private String description;

    private String type;

}
