package com.carlos.clientserviceapi.models.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Service
public class UploadServiceImpl implements IUploadFileService{

    private final String UPLOADS_PATH = "uploads";

    private final Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);

    @Override
    public Resource loadImage(String imageName) throws MalformedURLException {
        Path fileRoute = getPath(imageName);
        logger.info("User image uploaded to: " + fileRoute.toString());
        Resource resource = new UrlResource(fileRoute.toUri());

        if (!resource.exists() && !resource.isReadable()){
            fileRoute = Paths.get("src/main/resources/static/images").resolve("accounts_user_icon.ico").toAbsolutePath();

            resource = new UrlResource(fileRoute.toUri());

            logger.error("Error, image: " + imageName + " could not be loaded");
        }
        return resource;
    }

    @Override
    public String copyImage(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename().replace(" ", "");
        Path fileRoute = getPath(UPLOADS_PATH);
        logger.info("User image uploaded to: " + fileRoute);

        Files.copy(file.getInputStream(), fileRoute);

        return fileName;
    }

    @Override
    public boolean deleteImage(String imageName) {
        if (imageName != null && imageName.length() > 0) {
            Path previousRoute = Paths.get("uploads").resolve(imageName).toAbsolutePath();
            File previousFile = previousRoute.toFile();

            if (previousFile.exists() && previousFile.canRead()) {
                previousFile.delete();
                return true;
            }
        }
        return false;
    }

    @Override
    public Path getPath(String imageName) {
        return Paths.get(UPLOADS_PATH).resolve(imageName).toAbsolutePath();
    }
}
