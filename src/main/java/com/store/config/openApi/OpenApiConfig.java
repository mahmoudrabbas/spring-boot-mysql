package com.store.config.openApi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Value("${app.title}")
    private String appTitle;
    @Value("${app.version}")
    private String version;
    @Value("${app.description}")
    private String description;
    @Value("${app.developer.name}")
    private String appDeveloperName;
    @Value("${app.developer.email}")
    private String appDeveloperEmail;
    @Value("${app.developer.github}")
    private String appDeveloperGithub;


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(appTitle)
                        .version(version)
                        .description(description)
                        .contact(new Contact()
                                .name(appDeveloperName)
                                .email(appDeveloperEmail)
                                .url(appDeveloperGithub))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org"))
                );
    }
}
