package com.kopylov.musicplatform.helper;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@UtilityClass
public class FileHelper {

    public void saveUploadedFile(MultipartFile file, String static_path) throws IOException {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(static_path + file.getOriginalFilename());
            Files.write(path, bytes);
        }
    }
}