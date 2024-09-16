package com.example.PlayFlix.services;

import com.example.PlayFlix.interfaces.IContenidoService;
import com.example.PlayFlix.models.entities.Contenido;
import com.example.PlayFlix.models.repository.ContenidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContenidoService implements IContenidoService {

    @Autowired
    private ContenidoRepository contenidoRepository;

    @Override
    public List<Contenido> findAll() {
        return contenidoRepository.findAll();
    }

    @Override
    public Contenido findById(Integer id) {
        return contenidoRepository.findById(id).orElse(null);
    }

    @Override
    public Contenido saveContenido(Contenido contenido) {
        return contenidoRepository.save(contenido);
    }

    @Override
    public Contenido updateContenido(Contenido contenido) {
        if (contenido.getId() != null && contenidoRepository.existsById(contenido.getId())) {
            return contenidoRepository.save(contenido);
        } else {
            throw new RuntimeException("Ups! Hay un error y no se puede actualizar");
        }
    }

    @Override
    public void delete(Integer id) {
        contenidoRepository.deleteById(id);
    }

    @Override
    public List<Contenido> isExist(Contenido contenido) {
        return contenidoRepository.findByNombreAndDescripcion(contenido.getNombre(), contenido.getDescripcion());
    }
}
