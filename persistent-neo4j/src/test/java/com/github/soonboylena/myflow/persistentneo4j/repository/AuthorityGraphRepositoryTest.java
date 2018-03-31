package com.github.soonboylena.myflow.persistentneo4j.repository;

import com.github.soonboylena.myflow.persistentneo4j.NeoBaseTest;
import com.github.soonboylena.myflow.persistentneo4j.entity.AuthorityEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

public class AuthorityGraphRepositoryTest extends NeoBaseTest {

    @Autowired
    AuthorityGraphRepository repository;

    private AuthorityEntity testObject;

    @Before
    public void save() {
        AuthorityEntity role = new AuthorityEntity("ROLE_role");
        role.setDescription("测试1");
        role.setTitle("角色");

        AuthorityEntity auth = new AuthorityEntity("permission");
        auth.setTitle("权限");
        role.addAuthority(auth);

        AuthorityEntity auth2 = new AuthorityEntity("permission2");
        auth2.setTitle("权限2");
        role.addAuthority(auth2);

        AuthorityEntity save = repository.save(role);
        testObject = save;

        print(save, "新提交的一条数据");
    }

    @Test
    public void query() {
        AuthorityEntity one = repository.findOne(testObject.getId());
        Assert.assertEquals("确认id相同", one.getId(), testObject.getId());
        print(one, "找到的数据");
//        repository.delete(one);
    }

    @Test
    public void remove() {
        Assert.assertNotNull(testObject);
        repository.delete(testObject);
    }


    @Test
    public void findAllByLabels() {
        List<AuthorityEntity> roles = repository.findAllRole();
        print(roles, "测试只取role");
        repository.delete(testObject);
        Assert.assertEquals(1, roles.size());
    }

    @Test
    public void findPermissionByRoleId() {
        List<AuthorityEntity> permissionByRoleId = repository.findPermissionByRoleId(testObject.getId());
        repository.delete(testObject);
        Assert.assertEquals(2,permissionByRoleId.size());


    }
}