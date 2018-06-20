package com.github.soonboylena.myflow.entity.custom;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Role {

    protected Long id;
    protected String express;
    protected String title;
    protected String description;
    protected Set<Permission> permissions = new HashSet<>();

    public void addPermission(Permission p) {
        permissions.add(p);
    }
}