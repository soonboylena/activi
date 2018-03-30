package com.github.soonboylena.myflow.persistentneo4j.repository;

import com.github.soonboylena.myflow.persistentneo4j.NeoBaseTest;
import com.github.soonboylena.myflow.persistentneo4j.entity.AuthorityEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthorityRepositoryTest  extends NeoBaseTest {

    @Autowired
    AuthorityGraphRepository repository;

    private AuthorityEntity testObject;

    @Before
    public void save() {
        AuthorityEntity entity = new AuthorityEntity();
        entity.setAuthority("anth1");
        entity.setDescription("测试1");
        entity.setTitle("权限1");

        AuthorityEntity save = repository.save(entity);
        testObject = save;

        print(save, "新提交的一条数据");
    }

    @Test
    public void query() {
        AuthorityEntity one = repository.findOne(testObject.getId());
        Assert.assertEquals("确认id相同", one.getId(), testObject.getId());
        print(one, "找到的数据");
    }


}