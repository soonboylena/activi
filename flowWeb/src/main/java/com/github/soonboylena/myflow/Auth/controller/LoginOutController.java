package com.github.soonboylena.myflow.Auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class LoginOutController {

    @RequestMapping("/loginProcess")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public String loginProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = "/system-login.html";
        if (isAjax(request)) {
            new DefaultRedirectStrategy().sendRedirect(request, response, "/system-login.html");
        }
        return path;
    }

    private boolean isAjax(HttpServletRequest request) {
        String requestedWithHeader = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(requestedWithHeader);
    }
}
