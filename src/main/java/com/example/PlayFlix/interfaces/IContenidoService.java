package com.example.PlayFlix.interfaces;

import com.example.PlayFlix.models.entities.Contenido;

import java.util.List;

public interface IContenidoService {

    List<Contenido> findAll();

    Contenido findById(Integer id);

    Contenido saveContenido(Contenido contenido);

    Contenido updateContenido(Contenido contenido);

    void delete(Integer id);

    public List<Contenido> isExist(Contenido curso);
}
