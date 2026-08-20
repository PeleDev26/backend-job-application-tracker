package com.pelegrin.job_application_tracker.dto.user;

import jakarta.validation.constraints.NotBlank;

public record PermissionRequest(

        @NotBlank(message = "Permission name cannot be blank")
        String name

) {}