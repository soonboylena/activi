package com.github.soonboylena.myflow.persistentneo4j.repository;

import com.github.soonboylena.myflow.persistentneo4j.entity.LoginInfoEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginInfoGraphRepository extends Neo4jRepository<LoginInfoEntity, Long> {

    public List<LoginInfoEntity> findAllByUsernameLike(String username);

//    public boolean existsByUsername(String username);

    public Boolean existsLoginInfoEntitiesByUsername(String username);

    public List<LoginInfoEntity> findByUsername(String username);
}
