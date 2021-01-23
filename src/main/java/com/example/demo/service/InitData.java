package com.example.demo.service;

import com.example.demo.entity.Article;
import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Classe permettant d'insérer des données dans l'application.
 */
@Service
@Transactional
public class InitData implements ApplicationListener<ApplicationReadyEvent> {

    private EntityManager entityManager;

    public InitData(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        insertTestData();
    }

    private void insertTestData() {
        Article a1 = createArticle("Chargeurs de téléphones Portables", 22.98, 9);
        Article a2 = createArticle("Playmobil ; Hydravion de Police", 14.39, 2);
        Article a3 = createArticle("Distributeur de croquettes ; pour chien", 12.99, 0);

        Client cl1 = createClient("John", "Doe", LocalDate.of(1900, Month.JANUARY, 1));
        Client cl2 = createClient("Adrienne", "Collet", LocalDate.of(2004, Month.APRIL, 25));
        Client cl3 = createClient("Valérie", "Brunet", LocalDate.of(1997, Month.JANUARY, 25));
        Client cl4 = createClient("Thierry-Eugène", "Hardy", LocalDate.of(1974, Month.JULY, 10));


        Map<Integer,Article> lf1 = new HashMap<>();
        lf1.put(3,a3);
        lf1.put(1,a1);
        Facture f1 = createFacture(cl2,lf1);

        Map<Integer,Article> lf2 = new HashMap<>();
        lf2.put(2,a2);
        Facture f2 = createFacture(cl4,lf2);

       Map<Integer,Article> lf3 = new HashMap<>();
       lf3.put(3,a2);
       lf3.put(10,a1);
       Facture f3 = createFacture(cl2,lf3);

        Map<Integer,Article> lf4 = new HashMap<>();
        lf4.put(3,a2);
        lf4.put(10,a1);
        Facture f4 = createFacture(cl2,lf3);


    }

    private Client createClient(String prenom, String nom, LocalDate naissance) {
        Client client = new Client();
        client.setPrenom(prenom);
        client.setNom(nom);
        client.setNaisssance(naissance);
        entityManager.persist(client);
        return client;
    }

    private Article createArticle(String libelle, double prix, int stock) {
        Article a1 = new Article();
        a1.setLibelle(libelle);
        a1.setPrix(prix);
        a1.setStock(stock);
        entityManager.persist(a1);
        return a1;
    }

    private Facture createFacture(Client client, Map<Integer, Article> articles) {
        Facture facture = new Facture();
        facture.setClient(client);
        LigneFacture ligneFacture = null;
        Set<LigneFacture> lignesFacture = new HashSet<>();

        for (Map.Entry map : articles.entrySet()){
            ligneFacture = new LigneFacture();
            ligneFacture.setArticle((Article) map.getValue());
            ligneFacture.setQuantite((Integer) map.getKey());
            ligneFacture.setFacture(facture);
            entityManager.persist(ligneFacture);
            lignesFacture.add(ligneFacture);
        }
        facture.setLigneFactures(lignesFacture);
        entityManager.persist(facture);
        return facture;
    }

}
