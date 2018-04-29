package com.github.soonboylena.myflow.persistentneo4j.repository;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "like")
@Getter
@Setter
public class LikeRelation {

    private Long id;

    @StartNode
    private PersonEntity p1;

    @EndNode
    private PersonEntity p2;

    @Property
    private String type;


    public LikeRelation(PersonEntity p1, PersonEntity p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public LikeRelation() {
    }
}
