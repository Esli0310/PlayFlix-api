package com.example.PlayFlix.controllers;

import com.example.PlayFlix.interfaces.IFavoritoService;
import com.example.PlayFlix.models.entities.Favorito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class FavoritoController {

    @Autowired
    private IFavoritoService favoritoService;

    @GetMapping("/favoritos")
    public List<Favorito> getAllFavoritos() {
        return favoritoService.findAll();
    }

    @GetMapping("/favoritos/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Favorito favorito = null;
        Map<String, Object> response = new HashMap<>();
        try {
            favorito = favoritoService.findById(id);
        } catch (DataAccessException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (favorito == null) {
            response.put("message", "El favorito con ID: " +
                    id.toString().concat(" no existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Favorito>(favorito, HttpStatus.OK);
    }

    @PostMapping("/favoritos")
    public ResponseEntity<?> save(@RequestBody Favorito favorito) {
        Map<String, Object> response = new HashMap<>();
        Favorito favoritoPersisted = null;
        try {
            favoritoPersisted = favoritoService.save(favorito);
        } catch (DataAccessException e) {
            response.put("error", "Error: " + e.getMessage());
        }
        if (favoritoPersisted != null) {
            response.put("message", "Favorito registrado correctamente..!");
            response.put("favorito", favoritoPersisted);
        } else {
            response.put("message", "Error al registrar o actualizar");
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/favoritos/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            favoritoService.delete(id);
        } catch (DataAccessException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Favorito eliminado correctamente");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
    }
}
