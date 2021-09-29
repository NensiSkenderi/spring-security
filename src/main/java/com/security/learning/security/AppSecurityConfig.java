package com.security.learning.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.security.learning.security.ApplicationUserPermission.*;
import static com.security.learning.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // to use preauthorize annotation on method level
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                //THE ORDER THAT WE DEFINE MATCHES MATTERS, it goes thru the matchers one by one
                .antMatchers("/", "index", "/css/", "/js").permitAll()
                .antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())
//                .antMatchers(HttpMethod.GET, "/management/**").hasAnyRole(ADMIN.name(), TRAINEE.name())
//                .antMatchers(HttpMethod.POST, "/management/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT, "/management/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.DELETE, "/management/**").hasAuthority(COURSE_WRITE.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        //this is how we retrieve our users from the database
        //return super.userDetailsService()
        UserDetails nensi = User.builder()
                .username("nensi")
                .password(passwordEncoder.encode("nensi"))
                .roles(STUDENT.name()) //this is ROLE_STUDENT
                .build(); //does not read or write to courses

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                //.roles(ADMIN.name()) //this is ROLE_ADMIN
                .authorities(ADMIN.getGrantedAuthorities())
                .build(); //read and wwrite to courses

        UserDetails trainee = User.builder()
                .username("trainee")
                .password(passwordEncoder.encode("trainee"))
                //.roles(TRAINEE.name()) //this is ROLE_TRAINEE
                .authorities(TRAINEE.getGrantedAuthorities())
                .build(); //only reads t courses

        return new InMemoryUserDetailsManager(nensi, admin, trainee);
    }
}
