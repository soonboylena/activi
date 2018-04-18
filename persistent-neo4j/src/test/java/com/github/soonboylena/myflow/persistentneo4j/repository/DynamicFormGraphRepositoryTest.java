package com.github.soonboylena.myflow.persistentneo4j.repository;

import com.github.soonboylena.myflow.persistentneo4j.NeoBaseTest;
import com.github.soonboylena.myflow.persistentneo4j.entity.DynamicEntity;
import com.github.soonboylena.myflow.persistentneo4j.entity.DynamicRelation;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DynamicFormGraphRepositoryTest extends NeoBaseTest {


    @Autowired
    DynamicFormGraphRepository repository;

    @Test
    public void saveRelatedDynamicForm() {

        DynamicEntity entity = new DynamicEntity("测试1", "test1");
        entity.addProperty("test1A", "string1");

        DynamicEntity entity2 = new DynamicEntity("测试2", "test2");
        entity2.addProperty("test2A", "string2");

        entity.addRelation("test2", entity2);

        repository.save(entity);
    }

    @Test
    public void findByLabel() {
        List<DynamicEntity> company2 = repository.findByLabel("company2");
        Assert.assertTrue(company2 != null && company2.size() > 0);
        print(company2, "取company2");
    }
}