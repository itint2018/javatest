package ru.itintego.javatest.services;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SecurityHelper {

    public static enum Role {
        ROLE_EMPLOYEE, ROLE_MANAGER, ROLE_ADMIN;
    }

    private Collection<? extends GrantedAuthority> authorities =
            Collections.emptyList();
    private final Authentication authentication;

    public SecurityHelper(SecurityContext context) {
        authentication = context.getAuthentication();
        if (authentication != null) {
            authorities = authentication.getAuthorities();
        }
    }

    public boolean isAuthenticated() {
        return authentication != null && authentication.isAuthenticated();
    }

    public boolean isAnonymous() {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    public String username() {
        return authentication == null ? "" : authentication.getName();
    }

    public boolean isAdminOrSuper() {
        return hasAnyRole(Arrays.asList(Role.ROLE_ADMIN, Role.ROLE_MANAGER));
    }

    public boolean hasRole(Role role) {
        return authorities != null && authorities.stream().anyMatch(authority ->
                authority.getAuthority().equals(role.name()));
    }

    public boolean hasAnyRole(List<Role> roles) {
        return roles.stream().anyMatch(this::hasRole);
    }
}
