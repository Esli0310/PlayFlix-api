package com.example.PlayFlix.interfaces;

import com.example.PlayFlix.models.entities.Usuario;

import java.util.List;

public interface IUsuarioService {

    List<Usuario> findAll();

    Usuario findById(Integer id);

    Usuario saveOrUpdate(Usuario usuario);

    void delete(Integer id);

}
