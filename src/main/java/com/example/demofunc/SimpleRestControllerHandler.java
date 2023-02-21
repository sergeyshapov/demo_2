package com.example.demofunc;

import com.example.demofunc.model.ModelOne;
import com.example.demofunc.model.ModelTwo;
import com.example.demofunc.security.AuthUser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class SimpleRestControllerHandler {

    public Mono<ServerResponse> method1(ServerRequest rq) {
        return rq.principal().flatMap(principal -> {
            var p = (AuthUser)principal;
            return ServerResponse.status(HttpStatus.OK).bodyValue(p.getName());
        });
    }

    public Mono<ServerResponse> method2(ServerRequest rq) {
        var data = rq.bodyToMono(ModelOne.class);
        return rq.principal().flatMap(principal -> {
            var p = (AuthUser)principal;
            return data.flatMap(value -> ServerResponse.status(HttpStatus.OK).bodyValue(new ModelTwo(value.getName(), value.getAge(), "Hello " + p.getName())));
        });

    }
}
