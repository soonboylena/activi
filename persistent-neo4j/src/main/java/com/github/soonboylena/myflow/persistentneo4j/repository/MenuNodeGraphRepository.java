package com.github.soonboylena.myflow.persistentneo4j.repository;

import com.github.soonboylena.myflow.persistentneo4j.entity.MenuNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MenuNodeGraphRepository extends Neo4jRepository<MenuNode, Long> {

    public boolean existsByCurrentKey(String currentKey);

    /**
     * 第3层节点（右侧按钮）
     */
    @Query("match(:MenuNode)-->(p:MenuNode)-[y:AUTHORITY_ENTITY]->(q:AuthorityEntity) where q.express in {0}  return *")
    public List<MenuNode> findMenuByExpress(Set<String> entitySet);

    /**
     * 只包含二层结点
     *
     * @param entitySet
     * @return
     */
    @Query("match(n:MenuNode)-->(p:MenuNode)-[y:AUTHORITY_ENTITY]->(q:AuthorityEntity) where q.express in {0}  return n")
    public List<MenuNode> findLevel2MenuByExpress(Set<String> entitySet);

//    @Query("match(p:MenuNode)-[x:AUTHORITY_ENTITY]->(n) where n.express = {0}  return p")
//    public List<MenuNode> findMenuTreesByExpress(String express);

//    @Query("match (n:MenuNode)-[s:ITEMS]->(x:MenuNode)-[a:AUTHORITY_ENTITY]->(p:AuthorityEntity) return n,s,x")
//    @Query("match(t:MenuNode)-[i:ITEMS]-(s)-[y:AUTHORITY_ENTITY]->(q:AuthorityEntity) return  *")

    /**
     * 返回左侧菜单和菜单对应的下级菜单
     * FIXME 总是有问题，有冗余的数据；先在java里边手动过滤一下
     *
     * @return
     */
    @Query("match p= ()-[*0..]-()-[*0..]->(:AuthorityEntity) return p")
    public List<MenuNode> findAllMenuNodeAndItem();

}


