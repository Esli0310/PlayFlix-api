package com.example.PlayFlix.controllers;

import com.example.PlayFlix.interfaces.IUsuarioService;
import com.example.PlayFlix.models.entities.Usuario;
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
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/usuarios")
    public List<Usuario> getAllUsuarios() {
        return usuarioService.findAll();
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Usuario usuario = null;
        Map<String, Object> response = new HashMap<>();
        try {
            usuario = usuarioService.findById(id);
        } catch (DataAccessException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (usuario == null) {
            response.put("message", "El usuario con ID: " +
                    id.toString().concat(" no existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }

    @PostMapping("/usuarios")
    public ResponseEntity<?> saveOrUpdate(@RequestBody Usuario usuario) {
        Map<String, Object> response = new HashMap<>();
        Usuario usuarioPersisted = null;
        try {
            usuarioPersisted = usuarioService.saveOrUpdate(usuario);
        } catch (DataAccessException e) {
            response.put("error", "Error: " + e.getMessage());
        }
        if (usuarioPersisted != null) {
            response.put("message", "Usuario registrado correctamente..!");
            response.put("usuario", usuarioPersisted);
        } else {
            response.put("message", "Error al registrar o actualizar");
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            usuarioService.delete(id);
        } catch (DataAccessException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Usuario eliminado correctamente");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
    }

}
