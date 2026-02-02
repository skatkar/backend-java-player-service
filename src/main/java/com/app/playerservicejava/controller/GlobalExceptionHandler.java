package com.app.playerservicejava.controller;

import com.app.playerservicejava.exception.ServiceException;
import com.app.playerservicejava.model.ApiError;
import io.github.ollama4j.exceptions.OllamaBaseException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex) {
        logger.error("Validation error: ", ex);

        StringBuilder errorMessage = new StringBuilder("Validation failed: ");
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errorMessage.append(violation.getMessage()).append("; ");
        }

        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "INVALID_INPUT_DATA",
                errorMessage.toString().trim(),
                Instant.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }


    @ExceptionHandler(OllamaBaseException.class)
    public ResponseEntity<ApiError> handleOllamaBaseException(OllamaBaseException ollamaBaseException){
        logger.error("Handling OllamaBaseException ", ollamaBaseException);

        ApiError apiError = new ApiError(HttpStatus.SERVICE_UNAVAILABLE.value(),
                "AI_SERVICE_ERROR",
                "AI Service is currently unavailable",
                Instant.now());

        return ResponseEntity.
                status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(apiError);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ApiError> handleOllamaBaseException(IOException ioException){
        logger.error("Handling IOException ", ioException);

        ApiError apiError = new ApiError(HttpStatus.SERVICE_UNAVAILABLE.value(),
                "AI_SERVICE_DOWN",
                "Unable to connect to AI service ATM",
                Instant.now());

        return ResponseEntity.
                status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(apiError);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiError> handleServiceException(ServiceException ioException){
        logger.error("Handling IOException ", ioException);

        ApiError apiError = new ApiError(HttpStatus.SERVICE_UNAVAILABLE.value(),
                "PLAYER_FETCHING_ERROR",
                "Server side error ATM",
                Instant.now());

        return ResponseEntity.
                status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(apiError);
    }

    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity<ApiError> handleInterruptedException(InterruptedException interruptedException){
        Thread.currentThread().interrupt();

        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "REQUEST_INTERRUPTED",
                "Request was interrupted",
                Instant.now());

        return ResponseEntity.
                status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiError);
    }
}
