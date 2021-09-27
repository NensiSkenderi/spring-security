package com.security.learning.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity //basic auth
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
               .authorizeRequests()
               .antMatchers("/","index","/css/","/js").permitAll()
               .anyRequest()
               .authenticated()
               .and()
               .httpBasic();//i want to use basic auth
        //so the client needs to specify username and passw

        //here you dont have the option to logout because username
        //and passw is sent to every request
    }
}
