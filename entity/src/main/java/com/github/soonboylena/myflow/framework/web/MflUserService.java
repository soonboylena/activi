package com.github.soonboylena.myflow.framework.web;

import com.github.soonboylena.myflow.entity.custom.MflUser;

/**
 * 用户、权限等处理接口
 */
public interface MflUserService {

    public MflUser saveUser(MflUser user);
}
