package com.pelegrin.job_application_tracker.dto;

import java.time.LocalDateTime;

public record ApiResponse<T>(
        boolean success,
        int status,
        String message,
        T data,
        LocalDateTime timestamp) {

    public static <T> ApiResponse<T> success(
            int status,
            String message,
            T data) {
        return new ApiResponse<>(
                true,
                status,
                message,
                data,
                LocalDateTime.now());
    }

    public static <T> ApiResponse<T> error(
            int status,
            String message,
        T data) {
        return new ApiResponse<>(
                false,
                status,
                message,
                data,
                LocalDateTime.now());
    }
}