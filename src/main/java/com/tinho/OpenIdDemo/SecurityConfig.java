package com.tinho.OpenIdDemo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import com.vaadin.flow.spring.security.VaadinWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends VaadinWebSecurity {
    private static final String LOGIN_URL = "/login";
    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.oauth2Login(c -> c.loginPage(LOGIN_URL).permitAll());
    }
}