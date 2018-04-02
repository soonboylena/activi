package com.github.soonboylena.myflow.Auth.service;

import com.github.soonboylena.myflow.Auth.bean.Role;
import com.github.soonboylena.myflow.persistentneo4j.entity.AuthorityEntity;
import com.github.soonboylena.myflow.persistentneo4j.repository.AuthorityGraphRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 机于Neo4j的实现
 */
@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    @Autowired
    private AuthorityGraphRepository repository;

    //    @CacheEvict(value = "MenuService_getTopMenu", allEntries = true)
    @Override
    public Role saveRole(Role role) {

        AuthorityEntity roleEntity = toDb(role);
        AuthorityEntity bak = repository.save(roleEntity);
        role.setId(bak.getId());
        logger.debug("保存成功，id：{}", role.getId());
        return role;
    }


    @Override
    public List<Role> findAllRoles() {
        List<AuthorityEntity> roleEntityList = repository.findAllRole();
        List<Role> collect = roleEntityList.stream().map(this::fromDb).collect(Collectors.toList());
        logger.debug("取得所有角色，size：{}", collect.size());
        return collect;
    }


    @Override
    public List<String> findRoleMenu(Long roleId) {
//        AuthorityEntity one = repository.findOne(roleId);
//        if (one == null) {
//            return null;
//        }

        List<AuthorityEntity> authorities = repository.findPermissionByRoleId(roleId);

        List<String> menuKeys = new ArrayList<>();
        if (null != authorities) {
            authorities.forEach(element -> menuKeys.add(element.getExpress()));
        }
        return menuKeys;
    }

    @Override
    public Role findRoleByRoleName(String express) {
        AuthorityEntity roleEntity = repository.findFirstByExpress(express);
        return fromDb(roleEntity);
    }

    @Override
    public Role findRoleById(Long id) {
        Optional<AuthorityEntity> byId = repository.findById(id);
        if (!byId.isPresent()) return null;
        return fromDb(byId.get());
    }

    private AuthorityEntity toDb(Role role) {
        AuthorityEntity entity = new AuthorityEntity(role.getExpress());
        entity.setTitle(role.getTitle());
        entity.setDescription(role.getDescription());
        return entity;
    }

    private Role fromDb(AuthorityEntity entity) {
        Role role = new Role();
        role.setId(entity.getId());
        role.setTitle(entity.getTitle());
        role.setExpress(entity.getExpress());
        role.setDescription(entity.getDescription());
        return role;
    }
}
