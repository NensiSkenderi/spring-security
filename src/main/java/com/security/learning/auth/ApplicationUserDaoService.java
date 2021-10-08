package com.security.learning.auth;

import java.util.Optional;

public interface ApplicationUserDaoService {
    Optional<ApplicationUser> selectAppUserByUsername(String username);
}
