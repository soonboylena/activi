package com.github.soonboylena.myflow.persistentneo4j.repository;

import com.github.soonboylena.myflow.persistentneo4j.entity.LoginInfoEntity;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginInfoGraphRepository extends GraphRepository<LoginInfoEntity> {

    public List<LoginInfoEntity> findAllByUsernameLike(String username);

    public boolean existsByUsername(String username);

    public List<LoginInfoEntity> findByUsername(String username);
}
