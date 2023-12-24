package com.mtxbjls.storeapi.controllers;

import com.mtxbjls.storeapi.exceptions.ResourceNotFoundException;
import com.mtxbjls.storeapi.services.IFileUploadService;
import com.mtxbjls.storeapi.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

@RestController
@RequestMapping(Constants.Endpoints.FILES)
@RequiredArgsConstructor
public class FileUploadController {

    private final IFileUploadService fileUploadService;

    @PostMapping(Constants.Endpoints.UPLOAD)
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadFile(@RequestPart("file") MultipartFile file) throws IOException {
        return fileUploadService.storeFile(file);
    }

    @GetMapping(Constants.PathVariables.FILENAME)
    public ResponseEntity<Resource> getFile(@PathVariable("fileName") String fileName) {
        Path filePath = fileUploadService.getFilePath(fileName);
        Resource resource;

        try {
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new ResourceNotFoundException("File not found");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
