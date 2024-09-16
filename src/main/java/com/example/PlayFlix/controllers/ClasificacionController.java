package com.example.PlayFlix.controllers;

import com.example.PlayFlix.interfaces.IClasificacionService;
import com.example.PlayFlix.models.entities.Clasificacion;
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
public class ClasificacionController {

    @Autowired
    private IClasificacionService clasificacionService;

    @GetMapping("/clasificaciones")
    public List<Clasificacion> getAllClasificaciones() {
        return clasificacionService.findAll();
    }

    @GetMapping("/clasificaciones/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Clasificacion clasificacion = null;
        Map<String, Object> response = new HashMap<>();
        try {
            clasificacion = clasificacionService.findById(id);
        } catch (DataAccessException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (clasificacion == null) {
            response.put("message", "La clasificación con ID: " +
                    id.toString().concat(" no existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Clasificacion>(clasificacion, HttpStatus.OK);
    }

    @PostMapping("/clasificaciones")
    public ResponseEntity<?> save(@RequestBody Clasificacion clasificacion) {
        Map<String, Object> response = new HashMap<>();
        Clasificacion clasificacionPersisted = null;
        try {
            clasificacionPersisted = clasificacionService.save(clasificacion);
        } catch (DataAccessException e) {
            response.put("error", "Error: " + e.getMessage());
        }
        if (clasificacionPersisted != null) {
            response.put("message", "Clasificación registrada correctamente..!");
            response.put("clasificacion", clasificacionPersisted);
        } else {
            response.put("message", "Error al registrar");
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/clasificaciones/{id}")
    public ResponseEntity<?> update(@RequestBody Clasificacion clasificacion, @PathVariable Integer id){


        Clasificacion claActual = clasificacionService.findById(id);
        Clasificacion claUpdated = null;

        //Instancia de HashMap
        Map<String, Object> response = new HashMap<>();

        if (claActual == null){
            response.put("message", "No se puede actualizar la clasificacion con Id: " +
                    id.toString().concat(" no existe en la base de datos"));

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            Clasificacion claExiste = clasificacionService.findByNombre(clasificacion.getNombre());
            if (claExiste != null && !claExiste.getId().equals(id)){
                response.put("message", "Ya existe una clasificacion con este nombre, digite otro.");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
            } else {
                claActual.setNombre(clasificacion.getNombre());
                claUpdated = clasificacionService.save(claActual);
            }
        }catch (DataAccessException e){
            response.put("message", "Error al actualizar el registro, intente de nuevo.");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Clasificacion actualizada correctamente.");
        response.put("clasificacion", claUpdated);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
    }





    @DeleteMapping("/clasificaciones/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            clasificacionService.delete(id);
        } catch (DataAccessException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Clasificación eliminada correctamente");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}
