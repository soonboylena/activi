package com.github.soonboylena.myflow.persistentneo4j.repository;

import com.github.soonboylena.myflow.persistentneo4j.entity.AuthorityEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AuthorityGraphRepository extends Neo4jRepository<AuthorityEntity, Long> {

    @Query("MATCH (n:role) RETURN n")
    public List<AuthorityEntity> findAllRole();

    @Query("MATCH (p)-[r:include]->(n) where ID(p)={0} RETURN n LIMIT 100")
    public List<AuthorityEntity> findPermissionByRoleId(Long id);

    public AuthorityEntity findFirstByExpress(String express);

    @Query("MATCH (p:role)-[r:include]->(:AuthorityEntity) WHERE ID(p)={0} DELETE r")
    public void deletePermission(Long id);

}
