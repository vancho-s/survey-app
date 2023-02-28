package com.survey.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

import static springfox.documentation.builders.PathSelectors.any;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage("com.survey.app")).paths(any()).build()
            .apiInfo(new ApiInfo("Survey Application API's",
                "API's for the survey service", "1.0", null,
                new Contact("Survey Application","https://loclhost:8080", ""),
                null, null, new ArrayList<>()));
    }
}
