package com.example.lostandfound.security;

import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

public class TokenHeaderFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                     HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authToken != null && authToken.startsWith("Token ")) {
            getContext().setAuthentication(new TokenHeaderAuthentication(authToken.substring(6)));
        }
        filterChain.doFilter(request, response);
    }
}

