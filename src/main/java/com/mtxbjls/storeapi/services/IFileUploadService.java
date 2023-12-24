package com.mtxbjls.storeapi.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface IFileUploadService {
    public String storeFile(MultipartFile file) throws IOException;
    public Path getFilePath(String fileName);
}
