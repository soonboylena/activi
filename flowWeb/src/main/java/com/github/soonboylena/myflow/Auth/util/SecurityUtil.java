package com.github.soonboylena.myflow.Auth.util;

import com.github.soonboylena.myflow.Auth.bean.SecurityUserImpl;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static SecurityUserImpl currentUser() {
        return (SecurityUserImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String currentUserId() {
        return currentUser().getId().toString();
    }
}
