package com.example.demofunc.security;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

//@Component
public class DefaultProfileService implements ProfileService{
    @Override
    public Mono<Profile> getByUser(String name) {
        return Mono.empty();
    }
}
