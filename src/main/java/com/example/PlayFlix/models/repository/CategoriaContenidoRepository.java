package com.example.PlayFlix.models.repository;

import com.example.PlayFlix.models.entities.CategoriaContenido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaContenidoRepository extends JpaRepository<CategoriaContenido, Integer> {
}
