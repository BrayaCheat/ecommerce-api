package com.ecommerce.ecommerce.Utils;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {
    private final Path filePath = Paths.get("src/main/resources/static/images");
    private final String host = "http://127.0.0.1:8888/";

    public String uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("File not found.");
        }
        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath);
        }
        String fileName = file.getOriginalFilename();
        fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(fileName);
        Files.copy(file.getInputStream(), filePath.resolve(fileName));
        return host + "images/" + fileName;
    }
}