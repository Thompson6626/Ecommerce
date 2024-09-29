package com.ecommerce.ecommerce.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.ecommerce.ecommerce.user.User;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<User>{
    
    @Override
    public Optional<User> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
            .filter(auth -> auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken))
            .map(Authentication::getPrincipal)
            .filter(User.class::isInstance)  
            .map(User.class::cast);
    }
    
}
