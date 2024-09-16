package com.example.PlayFlix.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface IUploadFileService {
    String copyFile(MultipartFile file) throws IOException; //copiar el archivo para el servidor en la implentacion se ve
    boolean deteleFile(String fileName);
    public Path getPath(String fileName); //para la ruta
}
