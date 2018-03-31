package com.github.soonboylena.myflow.persistentneo4j.repository;

import com.github.soonboylena.myflow.persistentneo4j.entity.AuthorityEntity;
import com.sun.org.glassfish.gmbal.ParameterNames;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AuthorityGraphRepository extends GraphRepository<AuthorityEntity> {

    @Query("MATCH (n:role) RETURN n")
    public List<AuthorityEntity> findAllRole();

    @Query("MATCH (p)-[r:include]->(n) where ID(p)={0} RETURN n LIMIT 100")
    public List<AuthorityEntity> findPermissionByRoleId(Long id);

    public AuthorityEntity findFirstByExpress(String express);

}
