package com.example.PlayFlix.services;

import com.example.PlayFlix.interfaces.IUploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileService implements IUploadFileService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String UPLOADS_FOLDER = "src/main/resources/static/images/contenidos";
    @Override
    public String copyFile(MultipartFile file) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString() +"_"+ file.getOriginalFilename().replace(" ","");
        Path rootpath = getPath(uniqueFileName);
        log.info("rootPath: "+rootpath);

        Files.copy(file.getInputStream(), rootpath);
        return uniqueFileName;
    }

    @Override
    public boolean deteleFile(String fileName) {
        Path rootPath = getPath(fileName);
        File file = rootPath.toFile();
        if(file.exists() && file.canRead()){
            return file.delete();
        }
        return false;
    }

    @Override
    public Path getPath(String fileName) {
        return  Paths.get(UPLOADS_FOLDER).resolve(fileName).toAbsolutePath();
    }
}
