package com.example.rest.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openApiDescription() {
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Local env");

        Server productionServer = new Server();
        productionServer.setUrl("http://some.prod.url");
        productionServer.setDescription("Prod env");

        Contact contact = new Contact();
        contact.setName("Dima Utkin");
        contact.setEmail("dm.utkin@icloud.com");
        contact.setUrl("http://some.url");

        License license = new License().name("GNU AGPLv3").url("https://chooselicense.com/licenses/agpk-3.0/");

        Info info = new Info().title("Client-orders API").version("1.0").contact(contact).description("API for client`s orders")
                .termsOfService("http://some.terms.url")
                .license(license);

        return new OpenAPI().info(info).servers(List.of(localServer, productionServer));

    }
}
