package com.security.learning.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.TimeUnit;

import static com.security.learning.security.ApplicationUserPermission.COURSE_WRITE;
import static com.security.learning.security.ApplicationUserRole.ADMIN;
import static com.security.learning.security.ApplicationUserRole.TRAINEE;

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
                    .formLogin()
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/courses", true)
                    .passwordParameter("password") //taken from name of login.html, example : <input type="password" id="password" name="password"
                    .usernameParameter("usernameNensi")
                .and()
                .rememberMe()
                    .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21)) //extends session to 21 days
                    .key("smthverysecured")
                    .rememberMeParameter("remember-me")
                .and()
                .logout()
                        .logoutUrl("/logout") //not a best practice to use GET for loging out
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID","remember-me") //we remove cookies when we logout
                        .logoutSuccessUrl("/login"); //key which is used for md5 hash
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {

    }
}
