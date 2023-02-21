package com.example.demofunc.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig implements WebFluxConfigurer {

    private final AuthManager authManager;
    private final SecurityRepo securityRepo;
    private List<String> whiteList= List.of("/actuator/**",
            "/swager-resources/**",
            "/webjars/**",
            "/v2/api-docs",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/swagger-ui/index.html");

    public SecurityConfig(AuthManager authManager) {
        this.authManager = authManager;
        this.securityRepo = new SecurityRepo(authManager, whiteList);
    }

    @Bean
    public SecurityWebFilterChain securityFilterChainConfigurer(ServerHttpSecurity httpSecurity) {
        String[] arr = new String [whiteList.size()];
        whiteList.toArray(arr);

        SecurityWebFilterChain build = httpSecurity
                .cors().and().csrf().disable()
        .authorizeExchange()
                .pathMatchers(arr).permitAll()
                .anyExchange().authenticated()
                .and()
                .authenticationManager(authManager)
                .securityContextRepository(securityRepo)
                .build();
        return build;
    }

}
