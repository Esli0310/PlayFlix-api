package com.example.PlayFlix.interfaces;

import com.example.PlayFlix.models.entities.Comentario;

import java.util.List;

public interface IComentarioService {

    List<Comentario> findAll();

    Comentario findById(Integer id);

    Comentario save(Comentario comentario);

    void delete(Integer id);
}
