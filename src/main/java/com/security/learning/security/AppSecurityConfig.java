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
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
        CSRF - cross site request forgery(falsifikim) - the action of forging a copy or imitation of a doc, signature banknote
        by default CSRF is enabled. client logins, the server sends back a CSRF token, then the frontend (client)
        must submit form with token so that the server can validate if token sent and received is the same
         */
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/", "/js").permitAll()
                .antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())
                .antMatchers(HttpMethod.GET, "/management/**").hasAnyRole(ADMIN.name(), TRAINEE.name())
                .antMatchers(HttpMethod.POST, "/management/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/management/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.DELETE, "/management/**").hasAuthority(COURSE_WRITE.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .defaultSuccessUrl("/courses", true)
                .and()
                .rememberMe(); //default to 2 weeks
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
