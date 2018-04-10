package com.github.soonboylena.myflow.persistentneo4j.repository;

import com.github.soonboylena.myflow.persistentneo4j.NeoBaseTest;
import com.github.soonboylena.myflow.persistentneo4j.entity.DynamicEntity;
import com.github.soonboylena.myflow.persistentneo4j.entity.DynamicRelation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DynamicFormGraphRepositoryTest extends NeoBaseTest {


    @Autowired
    DynamicFormGraphRepository repository;

    @Test
    public void saveRelatedDynamicForm() {

        DynamicEntity entity = new DynamicEntity("测试1", "test1");
        entity.addProperty("test1A", "string1");

        DynamicEntity entity2 = new DynamicEntity("测试2", "test2");
        entity2.addProperty("test2A", "string2");

        DynamicRelation dynamicRelation = new DynamicRelation(entity, entity2, "test1");

        entity.addRelation(dynamicRelation);

        repository.save(entity);
    }

}