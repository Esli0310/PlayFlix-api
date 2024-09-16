package com.example.PlayFlix.models.repository;

import com.example.PlayFlix.models.entities.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Integer> {
}
