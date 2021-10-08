package com.security.learning.auth;

import com.google.common.collect.Lists;
import com.security.learning.security.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("fake") //repository tells spring that this class needs to be instantiated
//and the name fake is what you use when you autowire in case you have more than 1 impl
public class FakeApplicationUserDaoServiceImpl implements ApplicationUserDaoService {

    @Autowired // Field injection is not recommended  ?? check this
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<ApplicationUser> selectAppUserByUsername(String username) {
        return getAppUsers().stream().filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst(); //otherwise it will be an optional empty
    }

    private List<ApplicationUser> getAppUsers(){
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
                new ApplicationUser(
                        ApplicationUserRole.STUDENT.getGrantedAuthorities(),
                        passwordEncoder.encode("student"),
                        "student",
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        ApplicationUserRole.TRAINEE.getGrantedAuthorities(),
                        passwordEncoder.encode("trainee"),
                        "trainee",
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        ApplicationUserRole.ADMIN.getGrantedAuthorities(),
                        passwordEncoder.encode("admin"),
                        "admin",
                        true,
                        true,
                        true,
                        true
                )
        );
        return applicationUsers;
    }
}
