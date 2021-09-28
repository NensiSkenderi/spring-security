package com.security.learning.security;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.security.learning.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet()),  //student does not have any permissions
    ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE));

    //define permission

    private final Set<ApplicationUserPermission> permissions;//set because permissions must be unique

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }
}


