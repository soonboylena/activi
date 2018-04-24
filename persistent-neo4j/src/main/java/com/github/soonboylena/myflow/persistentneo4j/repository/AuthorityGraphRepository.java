package com.github.soonboylena.myflow.persistentneo4j.repository;

import com.github.soonboylena.myflow.persistentneo4j.entity.AuthorityEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AuthorityGraphRepository extends Neo4jRepository<AuthorityEntity, Long> {

    /**
     * 查询所有的角色
     * @return
     */
    @Query("MATCH (n:role) RETURN n")
    public List<AuthorityEntity> findAllRole();

    /**
     * 查询role底下的permission
     * @param id
     * @return
     */
    @Query("MATCH (p)-[r:include]->(n) where ID(p)={0} RETURN n LIMIT 100")
    public List<AuthorityEntity> findPermissionByRoleId(Long id);

    /**
     * 根据express查找AuthorityEntity
     * @param express
     * @return
     */
    public AuthorityEntity findFirstByExpress(String express);

    /**
     * 删除角色与权限的关系
     * @param id
     */
    @Query("MATCH (p:role)-[r:include]->(:AuthorityEntity) WHERE ID(p)={0} DELETE r")
    public void deletePermission(Long id);

}
