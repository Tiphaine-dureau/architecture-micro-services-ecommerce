package com.ecommerce.microcommerce.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/* @Configuration appliquée à la classe permet de remplacer un fichier de configuration classique en XML.
Elle nous donne accès à plusieurs méthodes très intéressantes pour la personnalisation de Swagger, grâce à la classe
Docket qui gère toutes les configurations.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                /* apis permet de filtrer la documentation à exposer selon les contrôleurs.
                Ainsi, vous pouvez cacher la documentation d'une partie privée ou interne de votre API.
                Dans ce cas, nous avons utilisé RequestHandlerSelectors.any().
                 */
                /*
                RequestHandlerSelectors est un prédicat (introduit depuis Java 8) qui permet de retourner TRUE ou FALSE
                selon la condition utilisée. Ici : ne rien documenter en dehors du package "web"
                 */
                .apis(RequestHandlerSelectors.basePackage("com.ecommerce.microcommerce.web"))
                /*
                : paths donne accès à une autre façon de filtrer : selon l'URI des requêtes.
                Ici : permet de passer une expression régulière qui n'accepte que les URI commençant par /Produits.
                 */
                .paths(PathSelectors.regex("/Produits.*"))
                .build();
    }
}
