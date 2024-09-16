package com.example.PlayFlix.services;

import com.example.PlayFlix.interfaces.IFavoritoService;
import com.example.PlayFlix.models.entities.Favorito;
import com.example.PlayFlix.models.repository.FavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoritoService implements IFavoritoService {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Override
    public List<Favorito> findAll() {
        return favoritoRepository.findAll();
    }

    @Override
    public Favorito findById(Integer id) {
        return favoritoRepository.findById(id).orElse(null);
    }

    @Override
    public Favorito save(Favorito favorito) {

        return favoritoRepository.save(favorito);
    }

    @Override
    public void delete(Integer id) {
        favoritoRepository.deleteById(id);
    }
}
