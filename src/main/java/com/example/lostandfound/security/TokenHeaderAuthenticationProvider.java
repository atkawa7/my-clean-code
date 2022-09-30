package com.example.lostandfound.security;

import com.example.lostandfound.domain.User;
import com.example.lostandfound.domain.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service("tokenHeaderAuthProvider")
public class TokenHeaderAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;

    public TokenHeaderAuthenticationProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        User user;
        if (auth instanceof TokenHeaderAuthentication tokenHeaderAuthentication &&
                (user = userRepository.findByToken(tokenHeaderAuthentication.getToken())) != null) {
                return new TokenHeaderAuthentication(user);

        }

        throw new BadCredentialsException("Invalid credentials supplied");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenHeaderAuthentication.class.isAssignableFrom(authentication);
    }
}
