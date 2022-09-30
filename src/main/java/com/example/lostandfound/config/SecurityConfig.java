package com.example.lostandfound.config;

import com.example.lostandfound.security.TokenHeaderFilter;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

@Configuration
public class SecurityConfig {
    public static final String H2_CONSOLE_URI = "/**/h2-console/**";
    public static final String FAVICON_ICO_URI = "/favicon.ico";
    public static final String SWAGGER_API_DOCS_URI = "/**/v3/api-docs/**";
    public static final String SWAGGER_CONFIGURATION_URI = "/**/configuration/ui";
    public static final String SWAGGER_RESOURCES_URI = "/**/swagger-resources/**";
    public static final String SWAGGER_CONFIGURATION_SECURITY_URI = "/**/configuration/security";
    public static final String SWAGGER_UI_URI = "/**/swagger-ui/**";
    public static final String SWAGGER_WEBJARS_URI = "/**/webjars/**";

    @Bean
    PasswordEncoder passwordEncoder(){
        return  createDelegatingPasswordEncoder();
    }

    @Bean
    OpenApiCustomiser sortSchemasAlphabetically() {
        return openApi -> openApi.getComponents()
                .addSecuritySchemes("api-key", new SecurityScheme()
                        .type(SecurityScheme.Type.APIKEY)
                        .name("Authorization")
                        .in(SecurityScheme.In.HEADER));
    }


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            @Qualifier("tokenHeaderAuthProvider")
                    AuthenticationProvider authenticationProvider) throws Exception {
        // @formatter:off
        return http.headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .cors()
                .and()
                .authenticationProvider(authenticationProvider)
                .exceptionHandling()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(
                        "/",
                        "/**/accounts/register",
                        "/**/accounts/login",
                        H2_CONSOLE_URI,
                        FAVICON_ICO_URI,
                        SWAGGER_API_DOCS_URI,
                        SWAGGER_CONFIGURATION_URI,
                        SWAGGER_RESOURCES_URI,
                        SWAGGER_CONFIGURATION_SECURITY_URI,
                        SWAGGER_UI_URI,
                        SWAGGER_WEBJARS_URI)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(STATELESS)
                .and()
                .addFilterAfter(new TokenHeaderFilter(), BasicAuthenticationFilter.class).build();

        // @formatter:on
    }


}