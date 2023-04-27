package com.carlos.clientserviceapi.models.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

public interface IUploadFileService {

    public Resource loadImage(String imageName) throws MalformedURLException;

    public String copyImage(MultipartFile file) throws IOException;

    public boolean deleteImage(String imageName);

    public Path getPath(String imageName);
}
