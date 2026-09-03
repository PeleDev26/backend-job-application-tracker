package com.pelegrin.job_application_tracker.validation;

import java.util.function.Predicate;

import org.springframework.http.HttpStatus;

import com.pelegrin.job_application_tracker.exception.AppException;

public final class ValidationUtils {

    private ValidationUtils() {
    }

    public static void validateNotExists(
            String value,
            Predicate<String> existsChecker,
            String message) {
        if (existsChecker.test(value)) {
            throw new AppException(
                    HttpStatus.CONFLICT, message);
        }
    }

}
