package com.example.PlayFlix.controllers;

import com.example.PlayFlix.interfaces.IContenidoService;
import com.example.PlayFlix.interfaces.IUploadFileService;
import com.example.PlayFlix.models.entities.Contenido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ContenidoController {

    @Autowired
    private IContenidoService contenidoService;

    @Autowired
    private IUploadFileService uploadFileService;

    @GetMapping("/contenidos")
    public List<Contenido> getAllContenidos() {
        return contenidoService.findAll();
    }

    @GetMapping("/contenidos/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Contenido contenido = null;
        Map<String, Object> response = new HashMap<>();
        try {
            contenido = contenidoService.findById(id);
        } catch (DataAccessException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (contenido == null) {
            response.put("message", "El contenido con ID: " +
                    id.toString().concat(" no existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Contenido>(contenido, HttpStatus.OK);
    }

    @PostMapping("/contenidos")
    public ResponseEntity<?> save(@RequestPart Contenido contenido,
                                  @RequestPart(name = "imagen", required = false) MultipartFile imagen) throws IOException {
        String imageNewName = null;
        Map<String, Object> response = new HashMap<>();
        try {
            if (contenidoService.isExist(contenido).size() > 0 && contenido.getId() == null) {
                response.put("message", "Ya existe un contenido igual");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);

            } else {
                if (imagen != null) {
                    imageNewName = uploadFileService.copyFile(imagen); //lo sube al servidor
                }
                contenido.setImagen(imageNewName);
                contenidoService.saveContenido(contenido);
            }
        } catch (DataAccessException e) {
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "El contendio se creo exitosamente...!");
        response.put("contenido", contenido);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    //metodo para actualizar un curso
    @PutMapping("/contenido/{id}")
    public ResponseEntity<?> save(@RequestPart Contenido contenido,
                                  @RequestParam(name = "imagen", required = false) MultipartFile imagen,
                                  @PathVariable Integer id) throws IOException {
        String imageNewName = null;
        Contenido contenidoActual = contenidoService.findById(id);
        Contenido updateContenido = null;
        Map<String, Object> response = new HashMap<>();
        if (contenidoActual == null) {
            response.put("message", "No se puede editar el contenido con ID: " + id.toString().concat("no existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            if (!contenidoService.isExist(contenido).isEmpty() && !contenidoService.isExist(contenido).contains(id)) {
                response.put("message", "Ya existe un contenido igual");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);

            } else {
                contenidoActual.setFechaLanzamiento(contenido.getFechaLanzamiento());
                contenidoActual.setNombre(contenido.getNombre());
                contenidoActual.setDescripcion(contenido.getDescripcion());
                contenidoActual.setDuracion(contenido.getDuracion());



                if (imagen != null) {
                    if (contenido.getImagen() != null && !contenidoActual.getImagen().isEmpty()) {
                        String imgAnterior = contenidoActual.getImagen();
                        //obtenemos la ruta de la imagen anterior
                        Path rutaImgAnterior = uploadFileService.getPath(imgAnterior);
                        //convirtienio a archivo
                        File archivoImgAnterior = rutaImgAnterior.toFile();
                        if (archivoImgAnterior.exists() && archivoImgAnterior.canRead()) {
                            archivoImgAnterior.delete();
                        }
                    }
                    imageNewName = uploadFileService.copyFile(imagen);
                    contenidoActual.setImagen(imageNewName);
                }
                updateContenido = contenidoService.saveContenido(contenidoActual);
            }

        } catch (DataAccessException e) {
            response.put("message", "Error al realizar la consulta a la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "El contenido ha sido actualizado exitosamente...!");
        response.put("contenido", updateContenido);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/contenidos/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            contenidoService.delete(id);
        } catch (DataAccessException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Contenido eliminado correctamente");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
    }
}
