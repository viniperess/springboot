package br.edu.ifsul.cstsi.tads_curso.api.infra.doc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .info(new Info()
                        .title("API de Gerenciamento de Cursos")
                        .description("API Rest para gerenciamento de cursos, incluindo funcionalidades de autenticação, gerenciamento de usuários e cursos.")
                        .contact(new Contact()
                                .name("Equipe de Desenvolvimento")
                                .email("equipe@ifsul.edu.br"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://localhost:8080/api/licenca")));
    }
}
