package com.moyamoyu.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {


        return new OpenAPI()
                .components(
                        new Components()
                                .addSecuritySchemes("Bearer Authentication", createTokenSecurityScheme())
                                .addSecuritySchemes("Refresh_Token",refreshTokenSecurityScheme()))
                .addSecurityItem(
                        new SecurityRequirement().addList("Bearer Authentication").addList("Refresh_Token"))
                .info(apiInfo());
    }

    private SecurityScheme createTokenSecurityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    SecurityScheme refreshTokenSecurityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.COOKIE)
                .name("refresh_token");
    }

    private Info apiInfo() {
        return new Info()
                .title("Swagger Ui - moyamoyu")
                .description("MoyaMoyu API Documentation");
    }
}
