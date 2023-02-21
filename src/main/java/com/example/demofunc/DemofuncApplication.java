package com.example.demofunc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
//@EnableWebFlux
@OpenAPIDefinition(info=@Info(title="Заголовок", version = "v 1.0", description = "описание"))
public class DemofuncApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemofuncApplication.class, args);
    }

}
