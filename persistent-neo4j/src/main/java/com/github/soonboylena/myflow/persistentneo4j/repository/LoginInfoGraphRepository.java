package com.github.soonboylena.myflow.persistentneo4j.repository;

import com.github.soonboylena.myflow.persistentneo4j.entity.LoginInfoEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginInfoGraphRepository extends Neo4jRepository<LoginInfoEntity, Long> {

    public List<LoginInfoEntity> findAllByUsernameLike(String username);

//    public boolean existsByUsername(String username);

    public Boolean existsLoginInfoEntitiesByUsername(String username);

    /**
     * 根据用户名，取登录信息，附带 角色、以及角色的权限
     * @param username
     * @return
     */
    @Query("MATCH p=(l:LoginInfoEntity)-[:has]->(:role)-[:include]->(:AuthorityEntity) WHERE l.username = {0} RETURN p ")
    public List<LoginInfoEntity> findByUsername(String username);
}
