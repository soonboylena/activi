package com.github.soonboylena.myflow.persistentneo4j.repository;

import com.github.soonboylena.myflow.persistentneo4j.NeoBaseTest;
import com.github.soonboylena.myflow.persistentneo4j.entity.AuthorityEntity;
import com.github.soonboylena.myflow.persistentneo4j.entity.LoginInfoEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class AuthorityGraphRepositoryTest extends NeoBaseTest {

    @Autowired
    AuthorityGraphRepository repository;

    @Autowired
    LoginInfoGraphRepository loginInfoGraphRepository;

    private AuthorityEntity testObject;

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
        save();
        AuthorityEntity one = repository.findById(testObject.getId()).orElseThrow(RuntimeException::new);
        Assert.assertEquals("确认id相同", one.getId(), testObject.getId());
        print(one, "找到的数据");
        repository.delete(one);
    }

    @Test
    public void remove() {
        save();
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
        save();
        List<AuthorityEntity> permissionByRoleId = repository.findPermissionByRoleId(testObject.getId());
        repository.delete(testObject);
        Assert.assertEquals(2, permissionByRoleId.size());
    }

    /**
     * 测试某人权限和角色
     */
    @Test
    public void getSomebodyRolePermission() {


        // 用户张三
        LoginInfoEntity userZhang3 = new LoginInfoEntity();
        userZhang3.setUsername("zhang3");
        userZhang3.setTitle("张三");

        // 权限1
        AuthorityEntity per1 = new AuthorityEntity("张权1", "zhangPer1");
        AuthorityEntity per2 = new AuthorityEntity("张权2", "zhangPer2");
        AuthorityEntity role = new AuthorityEntity("张角1", "ROLE_zhangRole");
        role.addAuthority(per1, per2);
        AuthorityEntity per9 = new AuthorityEntity("张权2", "zhangPer9");

        userZhang3.addAuthority(role, per9);
        LoginInfoEntity save = loginInfoGraphRepository.save(userZhang3);

        Long id = save.getId();
        Optional<LoginInfoEntity> byId = loginInfoGraphRepository.findById(id, 3);

        Assert.assertTrue(byId.isPresent());

        print(byId.get(), "查找张三");


    }

    @Test
    public void findAuthByMenuIds() {
        Set<Long> ids = new HashSet<>();
        ids.add(10L);
        ids.add(121L);
        Iterable<AuthorityEntity> authByMenuIds = repository.findAllById(ids);
        print(authByMenuIds, "根据节点绑定权限");
    }
}