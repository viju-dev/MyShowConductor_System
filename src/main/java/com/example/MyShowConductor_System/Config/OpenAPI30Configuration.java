package com.example.MyShowConductor_System.Config;

//import io.swagger.v3.oas.annotations.security.SecurityScheme;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.media.MediaType;

@Configuration
//@SecurityScheme(//for specific api this and some before each api
//        name = "Bearer Authentication",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        scheme = "bearer"
//)
public class OpenAPI30Configuration {//https://www.baeldung.com/openapi-jwt-authentication
    @Bean
    public OpenAPI customizeOpenAPI() {
        final String securitySchemeName = "bearerAuth";

//        ApiResponses apiResponses = new ApiResponses()
//                .addApiResponse("200", new ApiResponse()
//                        .description("OK")
//                        .content(new io.swagger.v3.oas.models.media.Content()
//                                .addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE, new MediaType())
//                                .addMediaType(org.springframework.http.MediaType.APPLICATION_XML_VALUE, new MediaType())));

        return new OpenAPI().components(new Components())

                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))); //.addResponses("myResponse", apiResponses.get("200"))
    }
}