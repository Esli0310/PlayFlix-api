package com.example.PlayFlix.controllers;

import com.example.PlayFlix.interfaces.ICategoriaContenidoService;
import com.example.PlayFlix.models.entities.CategoriaContenido;
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
public class CategoriaContenidoController {

    @Autowired
    private ICategoriaContenidoService categoriaContenidoService;

    @GetMapping("/categoria-contenidos")
    public List<CategoriaContenido> getAllCategoriaContenido() {
        return categoriaContenidoService.findAll();
    }

    @GetMapping("/categoria-contenidos/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        CategoriaContenido categoriaContenido = null;
        Map<String, Object> response = new HashMap<>();
        try {
            categoriaContenido = categoriaContenidoService.findById(id);
        } catch (DataAccessException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (categoriaContenido == null) {
            response.put("message", "El registro con ID: " +
                    id.toString().concat(" no existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<CategoriaContenido>(categoriaContenido, HttpStatus.OK);
    }

    @PostMapping("/categoria-contenidos")
    public ResponseEntity<?> saveOrUpdate(@RequestBody CategoriaContenido categoriaContenido) {
        Map<String, Object> response = new HashMap<>();
        CategoriaContenido categoriaContenidoPersisted = null;
        try {
            categoriaContenidoPersisted = categoriaContenidoService.saveOrUpdate(categoriaContenido);
        } catch (DataAccessException e) {
            response.put("error", "Error: " + e.getMessage());
        }
        if (categoriaContenidoPersisted != null) {
            response.put("message", "Registro guardado correctamente..!");
            response.put("categoriaContenido", categoriaContenidoPersisted);
        } else {
            response.put("message", "Error al guardar o actualizar");
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/categoria-contenidos/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            categoriaContenidoService.delete(id);
        } catch (DataAccessException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Registro eliminado correctamente");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
    }
}
