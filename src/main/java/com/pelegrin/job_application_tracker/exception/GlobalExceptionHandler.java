package com.pelegrin.job_application_tracker.exception;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pelegrin.job_application_tracker.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ==========================================================
    // Handle application-specific exceptions
    // ==========================================================

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<Void>> handleAppException(AppException exception) {
        int status = exception.getStatus().value();

        return ResponseEntity.status(status).body(
                ApiResponse.error(status, exception.getMessage(), null));
    }

    // ==========================================================
    // Handle unexpected exceptions
    // ==========================================================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception exception) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ApiResponse.error(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "An unexpected error occurred", null));
    }

    // ==========================================================
    // Handle authorization errors
    // ==========================================================

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDenied(
            AccessDeniedException exception) {

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        ApiResponse.error(
                                HttpStatus.FORBIDDEN.value(),
                                "You do not have the required permissions",
                                null));
    }

    // ==========================================================
    // Handle request validation errors
    // ==========================================================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage(),
                        (existing, replacement) -> existing));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse.error(
                                HttpStatus.BAD_REQUEST.value(),
                                "Validation failed",
                                errors));
    }

    // ==========================================================
    // Handle invalid authentication credentials
    // ==========================================================

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentials(BadCredentialsException exception) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ApiResponse.error(
                                HttpStatus.UNAUTHORIZED.value(),
                                "Invalid email or password",
                                null));
    }

    // ==========================================================
    // Handle invalid authentication credentials
    // ==========================================================

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiResponse<Void>> handleDisabledException(DisabledException exception) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ApiResponse.error(
                                HttpStatus.UNAUTHORIZED.value(),
                                "User account is disabled",
                                null));
    }
}
