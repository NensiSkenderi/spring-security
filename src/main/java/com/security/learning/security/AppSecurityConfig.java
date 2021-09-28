package com.security.learning.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity //basic auth
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "index", "/css/", "/js").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    //to create our in memory user we override this

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        //this is how we retrieve our users from the database
        //return super.userDetailsService()
        UserDetails nensi = User.builder()
                .username("nensi")
                .password(passwordEncoder.encode("nensi"))
                .roles(ApplicationUserRole.STUDENT.name()) //this is ROLE_STUDENT
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles(ApplicationUserRole.ADMIN.name()) //this is ROLE_ADMIN
                .build();

        return new InMemoryUserDetailsManager(nensi, admin);
    }
}
