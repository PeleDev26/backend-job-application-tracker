package com.pelegrin.job_application_tracker.dto.user;

import jakarta.validation.constraints.NotBlank;

public record RoleRequest(

    @NotBlank(message = "Role name cannot be blank") String name){}

