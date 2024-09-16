package com.example.PlayFlix.interfaces;

import com.example.PlayFlix.models.entities.Clasificacion;

import java.util.List;

public interface IClasificacionService {

    public List<Clasificacion> findAll();

    public Clasificacion findById(Integer id);

    public Clasificacion save(Clasificacion clasificacion);

    public Clasificacion findByNombre(String nombre);

    void delete(Integer id);
}