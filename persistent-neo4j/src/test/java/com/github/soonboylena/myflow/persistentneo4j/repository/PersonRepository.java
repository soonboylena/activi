package com.github.soonboylena.myflow.persistentneo4j.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends Neo4jRepository<PersonEntity, Long> {
}


