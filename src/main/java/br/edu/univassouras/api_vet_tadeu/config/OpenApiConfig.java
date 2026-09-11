package br.edu.univassouras.api_vet_tadeu.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestão e Adoção de Animais (Api-Vet-Tadeu)")
                        .version("1.0.0")
                        .description("Sistema desenvolvido para a Prova Prática de Desenvolvimento Java / Spring Boot da Univassouras. " +
                                "Permite cadastrar, consultar, atualizar e excluir animais disponíveis para adoção.")
                        .contact(new Contact()
                                .name("Universidade de Vassouras - Univassouras")
                                .url("https://univassouras.edu.br"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}
