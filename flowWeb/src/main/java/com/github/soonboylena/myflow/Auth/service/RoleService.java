package com.github.soonboylena.myflow.Auth.service;

import com.github.soonboylena.myflow.persistentneo4j.entity.AuthorityEntity;
import com.github.soonboylena.myflow.persistentneo4j.entity.RoleEntity;
import com.github.soonboylena.myflow.Auth.jpa.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);
    @Autowired
    private RoleRepository roleRepository;

    //    @CacheEvict(value = "MenuService_getTopMenu", allEntries = true)
    public RoleEntity saveRole(RoleEntity roleEntity) {
        RoleEntity bak = roleRepository.save(roleEntity);
        return bak;
    }

    public List<RoleEntity> findAllRoles() {
        List<RoleEntity> roleEntityList = roleRepository.findAll();
        return roleEntityList;
    }

    public List<String> findRoleMenu(String roleType) {
        RoleEntity roleEntity = findRoleByRoleName(roleType);
        if (roleEntity == null) {
            return null;
        }
        Set<AuthorityEntity> authorities = roleEntity.getAuthorities();
        List<String> menuKeys = new ArrayList<>();
        if (null != authorities) {
            // TODO 放开
//            authorities.forEach(element -> menuKeys.add(element.getMenu().getCurrentKey()));
        }
        return menuKeys;
    }

    public RoleEntity findRoleByRoleName(String roleType) {
        RoleEntity roleEntity = roleRepository.findRoleByAuthority(roleType);
        if (roleEntity == null) {
        }
        return roleEntity;
    }

    public RoleEntity findRoleById(Long id) {
        return roleRepository.findOne(id);
    }
}
