package com.davi.Screescreenmatch.Repository;

import com.davi.Screescreenmatch.model.clas.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie,Long> {

    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);
}
