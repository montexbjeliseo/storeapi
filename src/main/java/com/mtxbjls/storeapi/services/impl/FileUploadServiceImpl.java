package com.mtxbjls.storeapi.services.impl;

import com.mtxbjls.storeapi.exceptions.MandatoryFieldException;
import com.mtxbjls.storeapi.services.IFileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.mtxbjls.storeapi.utils.Constants.Storage.UPLOAD_DIR;
import static java.util.UUID.randomUUID;

@Service
public class FileUploadServiceImpl implements IFileUploadService {
    @Override
    public String storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new MandatoryFieldException("File cannot be empty");
        }
        try {
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            String fileExtension = fileName.substring(fileName.lastIndexOf("."));

            String newFileName = randomUUID().toString() + fileExtension;

            Path filePath = Paths.get(UPLOAD_DIR, newFileName);

            Files.write(filePath, file.getBytes());

            return newFileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Path getFilePath(String fileName) {
        return Paths.get(UPLOAD_DIR).resolve(fileName);
    }
}
