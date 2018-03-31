package com.github.soonboylena.myflow.Auth.controller;

import com.github.soonboylena.myflow.Auth.service.AuthorityService;
import com.github.soonboylena.myflow.Auth.service.MenuService;
import com.github.soonboylena.myflow.persistentneo4j.entity.AuthorityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lungern xiii.at.cn@gmail.com
 * @date 2018/2/2
 */

@RestController
@RequestMapping("/authority")
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private MenuService menuService;

    @GetMapping("/all")
    public List<AuthorityEntity> findAllAuthority() {
        return authorityService.findAllAuthority();
    }

    @GetMapping("/one/{id}")
    public AuthorityEntity findById(@PathVariable Long id) {
//        Menu menu = menuService.findMenuById(id);
//        AuthorityEntity authorityEntity = menu.getAuthorityEntity();
//        if (null != authorityEntity) {
//            //wake hibernate to fetch data for me
//            authorityEntity.toString();
//        }
//        return authorityEntity;
        return null;
    }
}
