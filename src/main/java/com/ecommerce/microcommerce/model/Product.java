package com.ecommerce.microcommerce.model;
import com.fasterxml.jackson.annotation.JsonFilter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// @JsonFilter("monFiltreDynamique")
// @Entity permet que la classe soit scannée et prise en compte
@Entity
public class Product {
    // @Id et @GeneratedValue afin que l'attribut id soit identifié en tant que clé unique autogénérée.
    @Id
    @GeneratedValue
    private int id;
    private String nom;
    private int prix;


    // information que nous ne souhaitons pas exposer
    private int prixAchat;

    // Constructeur par défaut
    public Product() {
    }

    // Constructeur pour les tests
    public Product(int id, String nom, int prix, int prixAchat) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.prixAchat = prixAchat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(int prixAchat) {
        this.prixAchat = prixAchat;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                '}';
    }
}
