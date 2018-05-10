package com.github.soonboylena.myflow.workflow.mflConfig;

import com.github.soonboylena.myflow.entity.custom.MflUser;
import com.github.soonboylena.myflow.entity.custom.register.UserRoleAware;
import org.activiti.engine.IdentityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivitiUserRoleAware implements UserRoleAware {

    private static final Logger logger = LoggerFactory.getLogger(ActivitiUserRoleAware.class);

    private IdentityService identityService;

    ActivitiUserRoleAware(IdentityService identityService) {
        this.identityService = identityService;
    }

    @Override
    public void beforeSaveUser(MflUser user) {
    }

    @Override
    public void afterSaveUser(MflUser user) {

        Long id = user.getId();
        String idStr = id.toString();

        org.activiti.engine.identity.User activitiUser = identityService.createUserQuery().userId(idStr).singleResult();
        logger.debug("id:{}, activiti里边用户名:{}", idStr, activitiUser == null ? "activiti里边没找到这个用户" : activitiUser.getFirstName());

        if (activitiUser == null) {
            logger.info("将在activiti库中建立id为 [{}] 的用户. 用户名: {} ", id.toString(), user.getUsername());
            activitiUser = identityService.newUser(user.getUsername());
        }

        activitiUser.setFirstName(user.getUsername());
        activitiUser.setLastName(user.getUsername());
        activitiUser.setPassword(user.getPassword());

        identityService.saveUser(activitiUser);
    }

}
