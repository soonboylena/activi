package com.github.soonboylena.myflow.persistentneo4j.repository;

import com.github.soonboylena.myflow.persistentneo4j.NeoBaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class LoginInfoGraphRepositoryTest extends NeoBaseTest {

    @Autowired
    LoginInfoGraphRepository repository;

    @Test
    public void existsByUsername() {
        Boolean exists = repository.existsLoginInfoEntitiesByUsername("user1");
        System.out.println(exists);
    }
}