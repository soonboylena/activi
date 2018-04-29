package com.github.soonboylena.myflow.persistentneo4j.repository;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
@Getter
@Setter
public class PersonEntity {
    private Long id;
    private String name;

    @Relationship(type = "like")
    private Set<LikeRelation> likes = new HashSet<>();

    public PersonEntity(String name) {
        this.name = name;
    }

    public PersonEntity() {
    }

    public void like(PersonEntity p2) {
        LikeRelation relation = new LikeRelation(this, p2);
        likes.add(relation);
    }
}
