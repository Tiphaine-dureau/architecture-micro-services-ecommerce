package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.dao.ProductDao;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
public class ProductController {
    @Autowired
    private ProductDao productDao;

    //Récupérer la liste des produits - filtre dynamique
    @RequestMapping(value = "/Produits", method = RequestMethod.GET)
    public MappingJacksonValue listeProduits() {
        // findAll est une opération JpaRepository qui permet de récupérer toutes les données de l'entité concernée. findAll() retourne un Iterable
        Iterable<Product> produits = productDao.findAll();
        // SimpleBeanPropertyFilter permet d'établir les règles de filtrage sur un Bean donné
        // serializeAllExcept exclut uniquement les propriétés que nous souhaitons ignorer
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        // SimpleFilterProvider indique à Jackson le Bean ou il doit appliquer le filter :
        // ici peut s'appliquer à tous les Bean annotés avec monFiltreDynamique
        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
        // Mise au format MappingJacksonValue du filtre pour avoir accès aux méthodes qui nous intéressent : ici setFilters()
        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
        produitsFiltres.setFilters(listDeNosFiltres);
        // retour de la liste filtrée
        return produitsFiltres;
    }

    // Récupérer un produit par son id
    @GetMapping(value = "/Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id) {
        Product produit = productDao.findById(id);
        if(produit==null) throw new ProduitIntrouvableException("Le produit avec l'id " + id + " est INTROUVABLE. Écran Bleu si je pouvais.");
        return produit;
    }

    @GetMapping(value = "test/produits/{prixLimit}")
    public List<Product> testeDeRequetes(@PathVariable int prixLimit) {
        return productDao.findByPrixGreaterThan(400);
    }

    // Ajouter un produit
    @PostMapping(value = "/Produits")
    // ResponseEntity est une classe qui hérite de HttpEntity, qui permet de définir le code HTTP à retourner
    public ResponseEntity<Void> ajouterProduit(@RequestBody Product product) {
        Product productAdded = productDao.save(product);
        if (productAdded == null) {
            // noContent permet de retourner le code 204 dans le cas où le produit ajouté est vide ou n'existe pas
            // build : construit le header et y ajoute le code choisi
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/id")
                .buildAndExpand(productAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping (value = "/Produits/{id}")
    public void supprimerProduit(@PathVariable int id) {
        productDao.deleteById(id);
    }

    @PutMapping (value = "/Produits")
    public void updateProduit(@RequestBody Product product)
    {
        productDao.save(product);
    }
}

