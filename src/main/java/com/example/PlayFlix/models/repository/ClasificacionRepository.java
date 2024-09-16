package com.example.PlayFlix.models.repository;

import com.example.PlayFlix.models.entities.Clasificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClasificacionRepository extends JpaRepository<Clasificacion, Integer> {

    Clasificacion findByNombre(String nombre);
}
