package com.mtisw1.mueblesStgo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadService {
    private String folder="uploadFiles//";  // Se establece el nombre de la carpeta donde se guardarán los archivos
    private final Logger logg = LoggerFactory.getLogger(UploadService.class); // Para mostrar la información
    // Y no tener que ocupar System.out.println

    public String save(MultipartFile file){
        if(!file.isEmpty()){
            try {
                byte [] bytes = file.getBytes();
                Path path = Paths.get(folder+file.getOriginalFilename());
                Files.write(path, bytes);
                logg.info("File saved.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "File saved correctly";
    }
}
