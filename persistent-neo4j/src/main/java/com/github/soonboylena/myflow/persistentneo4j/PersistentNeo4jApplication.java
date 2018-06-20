package com.github.soonboylena.myflow.persistentneo4j;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EntityScan("com.github.soonboylena.myflow.persistentneo4j.entity")
public class PersistentNeo4jApplication {

//    public static void main(String[] args) {
//        SpringApplication.run(PersistentNeo4jApplication.class, args);
//    }


}
