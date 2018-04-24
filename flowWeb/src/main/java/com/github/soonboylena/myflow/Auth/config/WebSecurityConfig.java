package com.github.soonboylena.myflow.Auth.config;

import com.github.soonboylena.myflow.Auth.security.AjaxAuthFailHandler;
import com.github.soonboylena.myflow.Auth.security.AjaxAuthSuccessHandler;
import com.github.soonboylena.myflow.Auth.security.UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .userDetailsService(userDetailsService)
            .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(new UnauthorizedEntryPoint("/login"))
            .and()
                .formLogin()
                .successHandler(new AjaxAuthSuccessHandler())
                .failureHandler(new AjaxAuthFailHandler())
                .usernameParameter("loginName")
                .passwordParameter("password")
//            .and()
//                .rememberMe()
//				.tokenRepository(persistentTokenRepository())
//				.tokenValiditySeconds()
//				.userDetailsService(userDetailsService)
            .and()
                .authorizeRequests()
                .antMatchers("/user/check/{username}", "/user/add", "/title", "/title/{templateId}/{titleKey}")
                .permitAll()
                .anyRequest()
                .authenticated()
            .and()
                .formLogin()
            .and()
                .logout()
                .logoutSuccessHandler(
                    (HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication)
                            -> httpServletResponse.setStatus(HttpServletResponse.SC_OK))
            .permitAll();
        // @formatter:on
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password(passwordEncoder().encode("1"))
                .authorities(
                        "menu-system-user",
                        "menu-system-auth",
                        "menu-customer-add",
                        "menu-customer-list"
                );
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/menuGroup/**"
        );
    }

//    @Bean
//    public PersistentTokenRepository persistentTokenRepository() {
//        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
//        tokenRepository.setDataSource(dataSource);
//        return tokenRepository;
//    }
}