package com.example.PlayFlix.controllers;

import com.example.PlayFlix.interfaces.IComentarioService;
import com.example.PlayFlix.models.entities.Comentario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ComentarioController {

    @Autowired
    private IComentarioService comentarioService;

    @GetMapping("/comentarios")
    public List<Comentario> getAllComentarios() {
        return comentarioService.findAll();
    }

    @GetMapping("/comentarios/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Comentario comentario = null;
        Map<String, Object> response = new HashMap<>();
        try {
            comentario = comentarioService.findById(id);
        } catch (DataAccessException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (comentario == null) {
            response.put("message", "El comentario con ID: " +
                    id.toString().concat(" no existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Comentario>(comentario, HttpStatus.OK);
    }

    @PostMapping("/comentarios")
    public ResponseEntity<?> save(@RequestBody Comentario comentario) {
        Map<String, Object> response = new HashMap<>();
        Comentario comentarioPersisted = null;
        try {
            comentarioPersisted = comentarioService.save(comentario);
        } catch (DataAccessException e) {
            response.put("error", "Error: " + e.getMessage());
        }
        if (comentarioPersisted != null) {
            response.put("message", "Comentario registrado correctamente..!");
            response.put("comentario", comentarioPersisted);
        } else {
            response.put("message", "Error al registrar o actualizar");
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/comentarios/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            comentarioService.delete(id);
        } catch (DataAccessException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Comentario eliminado correctamente");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
    }
}