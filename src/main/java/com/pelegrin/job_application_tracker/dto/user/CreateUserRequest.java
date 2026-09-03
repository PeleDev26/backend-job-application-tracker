package com.pelegrin.job_application_tracker.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateUserRequest(
        @NotBlank(message = "firstname cannot be blank") String firstName,
        @NotBlank(message = "lastName cannot be blank") String lastName,
        @NotBlank(message = "RUT is required") String rut,
        @Email(message = "ERROR") @NotBlank(message = "Email is required") String email,
        @NotBlank(message = "password is required") String password,
        @NotNull @Positive(message = "cityId must be a positive number") Long cityId) {
}