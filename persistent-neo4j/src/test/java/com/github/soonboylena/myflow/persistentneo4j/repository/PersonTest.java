package com.github.soonboylena.myflow.persistentneo4j.repository;

import com.github.soonboylena.myflow.persistentneo4j.NeoBaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ActiveProfiles("sunb")
public class PersonTest extends NeoBaseTest {

    @Autowired
    PersonRepository repository;

    private PersonEntity personEntity = null;

    public void insert() {

        PersonEntity p1 = new PersonEntity("张三");
        PersonEntity p2 = new PersonEntity("李四");

        p1.like(p2);

        PersonEntity save = repository.save(p1);

        personEntity = save;

    }

    @Test
    public void findTest() {
        insert();
        Long id = personEntity.getId();

        Optional<PersonEntity> byId = repository.findById(id, 2);
        PersonEntity p = byId.orElseThrow(() -> new RuntimeException("没取到"));

        print(p,"检索");


        delete();
    }

    public void delete() {

        if (personEntity != null) {
            repository.delete(personEntity);
            System.out.println("删除");
        }

    }
}
