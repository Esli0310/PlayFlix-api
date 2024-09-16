package com.example.PlayFlix.services;

import com.example.PlayFlix.interfaces.ICategoriaContenidoService;
import com.example.PlayFlix.models.entities.CategoriaContenido;
import com.example.PlayFlix.models.repository.CategoriaContenidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaContenidoService implements ICategoriaContenidoService {

    @Autowired
    private CategoriaContenidoRepository categoriaContenidoRepository;

    @Override
    public List<CategoriaContenido> findAll() {
        return categoriaContenidoRepository.findAll();
    }

    @Override
    public CategoriaContenido findById(Integer id) {
        return categoriaContenidoRepository.findById(id).orElse(null);
    }

    @Override
    public CategoriaContenido saveOrUpdate(CategoriaContenido categoriaContenido) {
        return categoriaContenidoRepository.save(categoriaContenido);
    }

    @Override
    public void delete(Integer id) {
        categoriaContenidoRepository.deleteById(id);
    }
}
