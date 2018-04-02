package com.github.soonboylena.myflow.persistentneo4j.repository;

import com.github.soonboylena.myflow.persistentneo4j.entity.AuthorityEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityGraphRepository extends Neo4jRepository<AuthorityEntity, Long> {

    @Query("MATCH (n:role) RETURN n")
    public List<AuthorityEntity> findAllRole();

    @Query("MATCH (p)-[r:include]->(n) where ID(p)={0} RETURN n LIMIT 100")
    public List<AuthorityEntity> findPermissionByRoleId(Long id);

    public AuthorityEntity findFirstByExpress(String express);

}
