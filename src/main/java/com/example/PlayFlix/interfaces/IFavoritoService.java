package com.example.PlayFlix.interfaces;

import com.example.PlayFlix.models.entities.Favorito;

import java.util.List;

public interface IFavoritoService {

    public List<Favorito> findAll();

    public Favorito findById(Integer id);

    public Favorito save(Favorito favorito);

    void delete(Integer id);
}
