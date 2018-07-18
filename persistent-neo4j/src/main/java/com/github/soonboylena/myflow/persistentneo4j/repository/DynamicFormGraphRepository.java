package com.github.soonboylena.myflow.persistentneo4j.repository;

import com.github.soonboylena.myflow.persistentneo4j.entity.DynamicEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DynamicFormGraphRepository extends Neo4jRepository<DynamicEntity, Long> {

    @Query("MATCH n = (p:DynamicEntity) WHERE {0} in labels(p) RETURN p")
    public List<DynamicEntity> findByLabel(String label);
}
