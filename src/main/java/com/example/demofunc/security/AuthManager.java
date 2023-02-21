package com.example.demofunc.security;

import io.micrometer.core.instrument.util.StringUtils;
import io.netty.util.internal.StringUtil;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.rmi.UnexpectedException;
import java.util.Base64;

@Service
public class AuthManager implements ReactiveAuthenticationManager {
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        if(StringUtils.isEmpty(authentication.getName()))
            throw new RuntimeException("Token does not exist");
        var authData = authentication.getName().replace("Basic ","");
        var bytes= Base64.getDecoder().decode(authData);
        String userName = new String(bytes);

        return Mono.just(new AuthUser(new Profile(userName)));

    }
}
