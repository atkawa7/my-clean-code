package com.example.lostandfound.services;

import com.example.lostandfound.api.CurrentUserService;
import com.example.lostandfound.domain.User;
import com.example.lostandfound.security.TokenHeaderAuthentication;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Service("currentUserService")
public class CurrentUserServiceImpl implements CurrentUserService {
    @Override
    public User current() {
            if (getContext().getAuthentication() instanceof TokenHeaderAuthentication authToken &&
                    authToken.isAuthenticated() &&
                    authToken.getPrincipal() instanceof User user) {
                return user;
            }
            throw new AccessDeniedException("The user is currently not logged in");

    }
}
