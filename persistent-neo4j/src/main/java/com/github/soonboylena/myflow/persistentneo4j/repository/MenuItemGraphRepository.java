package com.github.soonboylena.myflow.persistentneo4j.repository;

import com.github.soonboylena.myflow.persistentneo4j.entity.MenuItem;
import com.github.soonboylena.myflow.persistentneo4j.entity.MenuNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MenuItemGraphRepository extends Neo4jRepository<MenuItem, Long> {

    /**
     * 根据权限把Card 菜单项目取出来
     */
    @Query("match(n:MenuNode)-[x:ITEMS]->(p:MenuItem)-[y:AUTHORITY_ENTITY]->(q:AuthorityEntity) where q.express in {1} and n.currentKey = {0}  return p")
    public List<MenuItem> findMenuByParentKeyAndExpress(String key, Set<String> entitySet);

}
