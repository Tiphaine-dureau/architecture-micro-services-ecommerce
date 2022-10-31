### Objectif :  
Développement un mini-système d'e-commerce fondé sur l'architecture Microservices avec Spring Boot.  
Création d'un premier microservice qui gère les produits que nous allons proposer à la vente. Il doit pouvoir ajouter, supprimer, mettre à jour et afficher les produits.  

### Dépendances :
Spring Web - Spring data JPA - H2 Database    

Ajout manuel de la dépendance io.springfox (pour swagger 2) -> @EnableSwagger2 dans la classe contenant la méthode main.  
Ajout dans application properties de : spring.mvc.pathmatch.matching-strategy=ant_path_matcher   
Création d'une classe SwaggerConfig  
