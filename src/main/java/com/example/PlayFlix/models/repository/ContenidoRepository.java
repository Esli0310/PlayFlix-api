package com.example.PlayFlix.models.repository;

import com.example.PlayFlix.models.entities.Contenido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContenidoRepository extends JpaRepository<Contenido, Integer> {
    List<Contenido> findByNombreAndDescripcion(String nombre, String descripcion);
}
