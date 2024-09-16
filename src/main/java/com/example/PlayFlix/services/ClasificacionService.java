package com.example.PlayFlix.services;

import com.example.PlayFlix.interfaces.IClasificacionService;
import com.example.PlayFlix.models.entities.Clasificacion;
import com.example.PlayFlix.models.repository.ClasificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClasificacionService implements IClasificacionService {

    @Autowired
    private ClasificacionRepository clasificacionRepository;

    @Override
    public List<Clasificacion> findAll() {
        return clasificacionRepository.findAll();
    }

    @Override
    public Clasificacion findById(Integer id) {
        return clasificacionRepository.findById(id).orElse(null);
    }


    @Override
    public Clasificacion findByNombre(String nombre) {return clasificacionRepository.findByNombre(nombre);}


    @Override
    public Clasificacion save(Clasificacion clasificacion) {
        return clasificacionRepository.save(clasificacion);
    }

    @Override
    public void delete(Integer id) {
        clasificacionRepository.deleteById(id);
    }

}