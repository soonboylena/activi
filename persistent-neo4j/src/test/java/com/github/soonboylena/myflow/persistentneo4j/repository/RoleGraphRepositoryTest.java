package com.github.soonboylena.myflow.persistentneo4j.repository;

import com.github.soonboylena.myflow.persistentneo4j.NeoBaseTest;
import com.github.soonboylena.myflow.persistentneo4j.entity.AuthorityEntity;
import com.github.soonboylena.myflow.persistentneo4j.entity.RoleEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class RoleGraphRepositoryTest extends NeoBaseTest {

    @Autowired
    RoleGraphRepository roleGraphRepository;

//    @Autowired
//    AuthorityGraphRepository repository;

    RoleEntity testObj;

    @Before
    public void save() {

        RoleEntity roleEntity = new RoleEntity();

        AuthorityEntity entity = new AuthorityEntity();
        entity.setTitle("关联到role的权限");
        entity.setAuthority("auth-in-row");
        roleEntity.addAuthority(entity);

        roleEntity.setRoleName("testRole1");
        roleEntity.setAuthority("测试权限");

        RoleEntity save = roleGraphRepository.save(roleEntity);
        testObj = save;

        print(save, "新提交的一条数据");
    }

    @Test
    public void query() {
        RoleEntity one = roleGraphRepository.findOne(testObj.getId());
        Assert.assertEquals("确认id相同", one.getId(), testObj.getId());
        print(one, "找到的数据");
    }


}