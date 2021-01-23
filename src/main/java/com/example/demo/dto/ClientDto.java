package com.example.demo.dto;

import java.time.LocalDate;

/**
 * Classe permettant d'exposer des données au format JSON au client.
 */
public class ClientDto {
    private Long id;
    private String nom;
    private String prenom;
    private LocalDate naissance;

    public ClientDto(Long id, String nom, String prenom, LocalDate naissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.naissance = naissance;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getNaissance() { return naissance; }

    public void setNaissance(LocalDate naissance) { this.naissance = naissance; }
}
