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
    public ResponseEntity<List<Usuario>> getAll(){
        List<Usuario> userList = usuarioService.findAll();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        Usuario usuario = null;
        Map<String, Object> response = new HashMap<>();
        try{
            usuario = usuarioService.findById(id);
        }catch (DataAccessException e){
            response.put("message","Ocurrio un error al realizar la consulta a la base de datos");
            response.put("Error", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(usuario == null){
            response.put("message","No existe el usuario con el ID: " + id.toString().concat(" en la base de datos"));
            return new ResponseEntity<Map<String, Object>> (response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Usuario>(usuario,HttpStatus.CREATED);
    }

    //Metodo para añadir nuevos usuarios;
    @PostMapping("/usuarios")
    public ResponseEntity<?> save(@RequestBody Usuario usuario){
        Map<String, Object> response = new HashMap<>();
        try{
            List<Usuario> userExiste = usuarioService.findByUsernameOrEmail(usuario.getUsername(), usuario.getEmail());
            if(userExiste.isEmpty()){
                //Logica para agregar el usuario
                usuarioService.saveOrUpdate(usuario);

            }else if (userExiste.size() == 1){
                response.put("message", (userExiste.get(0).getUsername().equals(usuario.getUsername())
                        && (userExiste.get(0).getEmail().equals(usuario.getEmail())))
                        ? "Ya existe un usuario con este username y este email"
                        : (userExiste.get(0).getUsername().equals(usuario.getUsername())
                        ? "Ya exixte un usuario con este nombre" : "Ya existe un usuario con este email"));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
            }else {
                response.put("message", "Ya existe un usuario con este username y otro con este email");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
            }
        }catch (DataAccessException e){
            response.put("message","Ocurrio un error al tratar de crear un nuevo usuario");
            response.put("Error", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Usuario creado exitosamente");
        response.put("Se creo el usuario ", usuario.getUsername());
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    //LOGICA PARA ACTUALIZAR UN USUARIO
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<?> update(@RequestBody Usuario usuario, @PathVariable Integer id){
        Map<String, Object> response = new HashMap<>();
        Usuario current = usuarioService.findById(id);
        Usuario updated = null;
        try{
            List<Usuario> userExiste = usuarioService.findRepeated(usuario.getUsername(), usuario.getEmail(), id);
            if(userExiste.isEmpty()){
                current.setNombre(usuario.getNombre());
                current.setEmail(usuario.getEmail());
                current.setUsername(usuario.getUsername());
                current.setEnabled(usuario.getEnabled());
                current.setRole(usuario.getRole());
                updated = usuarioService.saveOrUpdate(usuario);
            }else if (userExiste.size() == 1){
                response.put("message", (userExiste.get(0).getUsername().equals(usuario.getUsername())
                        && (userExiste.get(0).getEmail().equals(usuario.getEmail())))
                        ? "Ya existe un usuario con este username y este email"
                        : (userExiste.get(0).getUsername().equals(usuario.getUsername())
                        ? "Ya exixte un usuario con este nombre" : "Ya esxiste un usuario con este email"));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
            }else {
                response.put("message", "Ya existe un usuario con este username y otro con este email");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
            }
        }catch (DataAccessException e){
            response.put("message","Ocurrio un error al tratar de crear un nuevo usuario");
            response.put("Error", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("usuario", updated);
        response.put("message", "Usuario actualizado exitosamente");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);

    }



    @DeleteMapping("usuarios/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        //obtenemos el usuario
        Usuario userActual = usuarioService .findById(id);
        Map<String, Object> response = new HashMap<>();
        try{
            response.put("message","No se pudo eliminar el usuario con el ID: " + id.toString().concat(", este no existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }catch (DataAccessException e){
            response.put("message","Ocurrio un error al tratar de crear un nuevo usuario");
            response.put("Error", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
