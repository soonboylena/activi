package com.github.soonboylena.myflow.Auth.controller;

import com.github.soonboylena.myflow.Auth.bean.Message;
import com.github.soonboylena.myflow.Auth.bean.WebUser;
import com.github.soonboylena.myflow.persistentneo4j.entity.AuthorityEntity;
import com.github.soonboylena.myflow.persistentneo4j.entity.LoginInfoEntity;
import com.github.soonboylena.myflow.Auth.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author lungern xiii.at.cn@gmail.com
 * @date 2018/2/2
 */

@RestController
@RequestMapping
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("/user/{id:\\d+}")
    public LoginInfoEntity getUser(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }


    @GetMapping("/users")
    public Iterable<LoginInfoEntity> listAll() {
//        if (name == null) {
//            List<LoginInfoEntity> userEntities = userService.listAllUser();
//            //waken hibernate to fetch data for me
//            userEntities.forEach(element -> element.getAuthorities().toString());
//            return userEntities;
//        }
        return userService.listAllUser();
    }


    @GetMapping("/user/isExist/{username}")
    public boolean checkCanUse(@PathVariable String username) {
        username = username.trim();
        return userService.exist(username);
    }

    @PostMapping("/user/add")
    public Message addUser(@RequestBody WebUser user) {

        String nickName = StringUtils.trim(user.getTitle());
        String userName = StringUtils.trim(user.getUsername());

        Objects.requireNonNull(nickName);
        Objects.requireNonNull(userName);

        user.setTitle(nickName);
        user.setUsername(userName);
        userService.saveUser(user);
        return new Message(200, "添加成功");
    }

    @PostMapping("/user/edit")
    public Message updateUser(@RequestBody LoginInfoEntity user) {
        return null;
    }

    @PostMapping("/user/changeAuthority")
    public Message changeUserAuthority() {
        return null;
    }

    @PostMapping("/user/changeRole/{userId:\\d+}")
    public Message changeUserRole(@PathVariable Long userId, @RequestBody List<AuthorityEntity> roleList) {
//        LoginInfoEntity user = userService.findUserById(userId);
//
//        user.setRoles(new HashSet<>(roleList));
//        LoginInfoEntity bak = userService.updateUser(user, false);
//        Message<Boolean> message;
//        if (bak == null) {
//            message = new Message<>("更新失败", false);
//        } else {
//            message = new Message<>("更新成功", true);
//        }
//        return message;
        return null;
    }

    @DeleteMapping("/user/del/{userId:\\d+}")
    public Message<Boolean> deleteUser(@PathVariable Long userId) {

        LoginInfoEntity user = userService.findUserById(userId);
        Message<Boolean> message;
        if (null != user) {
            user.setEnabled(false);
            userService.updateUser(user, true);
            message = new Message<>("删除成功", true);
        } else {
            message = new Message<>("删除失败", false);
        }
        return message;
    }

    @PostMapping("/user/reset")
    public Message<Boolean> resetUser(@RequestBody LoginInfoEntity loginInfoEntity) {
//        if (loginInfoEntity.getId() == 0) {
//            return new Message<>("用户id不能为空", false);
//        }
//        LoginInfoEntity temp = userService.findUserById(loginInfoEntity.getId());
//        if (!temp.getUsername().equals(loginInfoEntity.getUsername())) {
//            return new Message<>("用户名不能修改", false);
//        }
//        boolean shouldEncrypt = true;
//        if ("".equals(loginInfoEntity.getPassword()) || loginInfoEntity.getPassword() == null) {
//            loginInfoEntity.setPassword(temp.getPassword());
//            shouldEncrypt = false;
//        }
//        loginInfoEntity.setRoles(temp.getRoles());
//        LoginInfoEntity bak = userService.updateUser(loginInfoEntity, shouldEncrypt);
//        if (null == bak) {
//            return new Message<>("更新失败", false);
//        } else {
//            return new Message<>("更新成功", true);
//        }
        return null;
    }
}
