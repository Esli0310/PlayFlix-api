package com.example.PlayFlix.interfaces;

import com.example.PlayFlix.models.entities.CategoriaContenido;

import java.util.List;

public interface ICategoriaContenidoService {

    List<CategoriaContenido> findAll();

    CategoriaContenido findById(Integer id);

    CategoriaContenido saveOrUpdate(CategoriaContenido categoriaContenido);

    void delete(Integer id);
}
