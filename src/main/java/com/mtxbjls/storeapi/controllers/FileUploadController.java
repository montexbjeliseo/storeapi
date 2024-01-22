package com.mtxbjls.storeapi.controllers;

import com.mtxbjls.storeapi.exceptions.ResourceNotFoundException;
import com.mtxbjls.storeapi.services.IFileUploadService;
import com.mtxbjls.storeapi.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.nio.file.Path;

@Tag(name = Constants.Docs.Tags.FILES, description = Constants.Docs.Tags.FILES_DESCRIPTION)
@RestController
@RequestMapping(Constants.Endpoints.FILES)
@RequiredArgsConstructor
public class FileUploadController {

    private final IFileUploadService fileUploadService;

    @Operation(
            summary = Constants.Docs.Operations.FILES_UPLOAD,
            security = @SecurityRequirement(name = Constants.Docs.BEARER_AUTH)
    )
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.CREATED,
                description = Constants.Docs.ResponseDescriptions.FILE_UPLOADED
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.UNAUTHORIZED,
                description = Constants.Docs.ResponseDescriptions.UNAUTHORIZED
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.BAD_REQUEST,
                description = Constants.Docs.ResponseDescriptions.BAD_REQUEST
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.FORBIDDEN,
                description = Constants.Docs.ResponseDescriptions.FORBIDDEN
        )
    })
    @PostMapping(
            value = Constants.Endpoints.UPLOAD,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadFile(@RequestPart("file") MultipartFile file) throws IOException {
        return fileUploadService.storeFile(file);
    }

    @Operation(
            summary = Constants.Docs.Operations.FILES_DOWNLOAD
    )
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.SUCCESS,
                description = Constants.Docs.ResponseDescriptions.FILE_DOWNLOADED
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.NOT_FOUND,
                description = Constants.Docs.ResponseDescriptions.FILE_NOT_FOUND
        )
    })
    @GetMapping(value = Constants.PathVariables.FILENAME, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getFile(@PathVariable("fileName") String fileName) throws IOException {
        Path filePath = fileUploadService.getFilePath(fileName);
        Resource resource;
        if (!filePath.toFile().exists()) {
            throw new ResourceNotFoundException("File not found");
        }
        resource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
