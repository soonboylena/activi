package com.github.soonboylena.myflow.Auth.service;

import com.github.soonboylena.myflow.Auth.bean.Role;
import com.github.soonboylena.myflow.persistentneo4j.entity.AuthorityEntity;
import com.github.soonboylena.myflow.persistentneo4j.repository.AuthorityGraphRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.Authenticator;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * 基于Neo4j的实现
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

    /**
     * 将roleId对应的权限与权限关联,要求两者都提前存在
     *
     * @param roleId
     * @param authIds
     * @return
     */
    @Transactional
    public AuthorityEntity updateRoleWithPermissions(Long roleId, List<Long> authIds) {

        Optional<AuthorityEntity> byId = repository.findById(roleId);
        AuthorityEntity entity = byId.orElseThrow(() -> new RuntimeException("不存在的角色： id:  " + roleId));
        repository.deletePermission(roleId);
        logger.info("角色权限清空： 角色：[{}:{}:{}]", roleId, entity.getTitle(), entity.getExpress());

        entity.cleanPermissions();

        if (authIds == null || authIds.isEmpty()) {
            return entity;
        }

        Iterable<AuthorityEntity> authorityEntities = repository.findAllById(authIds);
        entity.addAuthority(
                StreamSupport
                        .stream(authorityEntities.spliterator(), false).toArray(AuthorityEntity[]::new));

        AuthorityEntity save = repository.save(entity);
        logger.info("角色权限变更： 角色：{}, 权限：{}", roleId, authIds);
        return save;
    }
}
