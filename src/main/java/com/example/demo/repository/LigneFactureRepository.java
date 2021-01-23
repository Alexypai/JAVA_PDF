package com.example.demo.repository;

import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository permettant l'interraction avec la base de données pour les articles.
 */
@Repository
public interface LigneFactureRepository extends JpaRepository<LigneFacture, Long> {

    List<LigneFacture> findAllByFacture(Optional<Facture> iterable);
}
