package com.github.soonboylena.myflow.persistentneo4j.repository;

import com.github.soonboylena.myflow.persistentneo4j.entity.MenuNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MenuNodeGraphRepository extends Neo4jRepository<MenuNode,Long> {

    public boolean existsByCurrentKey(String currentKey);


    /**
     * 根据权限把menu树建出来
     */
    @Query("match(p:MenuNode)-[x:AUTHORITY_ENTITY]->(n) where n.express in {0}  return *")
    public List<MenuNode> findMenuByExpress(Set<String> entitySet);

    @Query("match(p:MenuNode)-[x:AUTHORITY_ENTITY]->(n) where n.express = {0}  return p")
    public List<MenuNode> findMenuTreesByExpress(String express);
}
