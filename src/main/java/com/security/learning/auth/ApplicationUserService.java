package com.security.learning.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsService {

    private final ApplicationUserDaoService applicationUserDaoService;

    @Autowired //so now with this spring knows that we have an impl and is the FakeApplicationUserDaoService
    //so with the @Qualifier we are literally saying that we want to use the impl named "fake"
    public ApplicationUserService(@Qualifier("fake") ApplicationUserDaoService applicationUserDaoService) {
        this.applicationUserDaoService = applicationUserDaoService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserDaoService.selectAppUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found!"));
    }
}
