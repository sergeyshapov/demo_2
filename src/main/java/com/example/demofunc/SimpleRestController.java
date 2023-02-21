package com.example.demofunc;

import com.example.demofunc.model.ModelOne;
import com.example.demofunc.model.ModelTwo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class SimpleRestController {

    @Bean
    //http://127.0.0.1:8080/webjars/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/
    @RouterOperations({
            @RouterOperation(produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET,
                    beanClass = SimpleRestControllerHandler.class, beanMethod = "method1",
                    operation = @Operation(description = "Получение профиля", operationId = "method1", responses = {
                            @ApiResponse(responseCode = "200", description = "ok"),
                            @ApiResponse(responseCode = "400", description = "not ok"),
                    }, parameters = {@Parameter(in = ParameterIn.HEADER, name = HttpHeaders.AUTHORIZATION,
                            description = "авторизашка")})),

            @RouterOperation(produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST,
                    beanClass = SimpleRestControllerHandler.class, beanMethod = "method2",
                    operation = @Operation(description = "Отправка тела запроса", operationId = "method2", responses = {
                            @ApiResponse(responseCode = "200", description = "ok", content = @Content(schema = @Schema(implementation = ModelTwo.class))),
                            @ApiResponse(responseCode = "400", description = "not ok"),
                    }, parameters = {@Parameter(in = ParameterIn.HEADER, name = HttpHeaders.AUTHORIZATION,
                            description = "авторизашка")},
                        requestBody = @RequestBody(description="тело запроса",
                                content = @Content(schema = @Schema(implementation = ModelOne.class)))
                    ))}
    )
    public RouterFunction<ServerResponse> routerFunction(SimpleRestControllerHandler simpleRestControllerHandler) {
        return route()
                .GET("/api/v2/profiles", accept(MediaType.ALL), simpleRestControllerHandler::method1)
                .POST("/api/v2/test", accept(MediaType.ALL), simpleRestControllerHandler::method2)
                .build();

    }
}
