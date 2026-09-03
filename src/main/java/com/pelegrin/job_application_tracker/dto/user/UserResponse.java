package com.pelegrin.job_application_tracker.dto.user;

import java.util.UUID;

public record UserResponse(
    UUID id,
    String firstName,
    String lastName,
    String rut,
    String email,
    String phone,
    boolean isActive,
    // Long roleId,
    String roleName,
    // Long cityId,
    String cityName
) {
}
