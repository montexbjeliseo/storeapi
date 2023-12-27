package com.mtxbjls.storeapi.exceptions.controller;

import com.mtxbjls.storeapi.exceptions.MandatoryFieldException;
import com.mtxbjls.storeapi.exceptions.ResourceNotFoundException;
import com.mtxbjls.storeapi.exceptions.UniqueConstraintViolationException;
import com.mtxbjls.storeapi.exceptions.dto.ExceptionDTO;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
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

    @ExceptionHandler({MandatoryFieldException.class})
    protected ResponseEntity<?> handleException(MandatoryFieldException ex,
                                                WebRequest request) {
        ExceptionDTO message = new ExceptionDTO(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UniqueConstraintViolationException.class})
    protected ResponseEntity<?> handleException(UniqueConstraintViolationException ex,
                                                WebRequest request) {
        ExceptionDTO message = new ExceptionDTO(
                HttpStatus.CONFLICT.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ServletException.class})
    protected ResponseEntity<?> handleException(ServletException ex,
                                                WebRequest request) {
        ExceptionDTO message = new ExceptionDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({IOException.class})
    protected ResponseEntity<?> handleException(IOException ex,
                                                WebRequest request) {
        ExceptionDTO message = new ExceptionDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MalformedJwtException.class})
    protected ResponseEntity<?> handleException(MalformedJwtException ex,
                                                WebRequest request) {
        ExceptionDTO message = new ExceptionDTO(
                HttpStatus.UNAUTHORIZED.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
                                                }
}
