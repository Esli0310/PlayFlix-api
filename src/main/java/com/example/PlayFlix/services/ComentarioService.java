package com.example.PlayFlix.services;

import com.example.PlayFlix.interfaces.IComentarioService;
import com.example.PlayFlix.models.entities.Comentario;
import com.example.PlayFlix.models.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioService implements IComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Override
    public List<Comentario> findAll() {
        return comentarioRepository.findAll();
    }

    @Override
    public Comentario findById(Integer id) {
        return comentarioRepository.findById(id).orElse(null);
    }

    @Override
    public Comentario save(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    @Override
    public void delete(Integer id) {
        comentarioRepository.deleteById(id);
    }
}
