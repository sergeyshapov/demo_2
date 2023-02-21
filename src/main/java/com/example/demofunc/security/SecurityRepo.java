package com.example.demofunc.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.RequestPath;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class SecurityRepo implements ServerSecurityContextRepository {
    private final AuthManager authManager;
    private final List<PathPattern> whiteList= new ArrayList<>();

    public SecurityRepo(AuthManager authManager, List<String> wl) {
        this.authManager = authManager;
        PathPatternParser pp = new PathPatternParser();
        wl.forEach(p->whiteList.add(pp.parse(p)));
    }


    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return null;
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        var req = exchange.getRequest();
        RequestPath path = req.getPath();
        if(whiteList.stream().anyMatch(pathPattern -> pathPattern.matches(path.pathWithinApplication())))
            return Mono.empty();
        String authHeader = req.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        Authentication authentication = new UsernamePasswordAuthenticationToken(authHeader, authHeader);
        return this.authManager.authenticate(authentication).onErrorResume(err ->{
            return  Mono.empty();
        }).map(SecurityContextImpl::new);
    }
}
