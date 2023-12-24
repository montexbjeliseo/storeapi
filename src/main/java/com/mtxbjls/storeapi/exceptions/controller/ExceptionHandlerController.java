package com.mtxbjls.storeapi.exceptions.controller;

import com.mtxbjls.storeapi.exceptions.ResourceNotFoundException;
import com.mtxbjls.storeapi.exceptions.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ResourceNotFoundException.class})
    protected ResponseEntity<?> handleException(ResourceNotFoundException ex,
                                                WebRequest request) {
        ExceptionDTO message = new ExceptionDTO(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
