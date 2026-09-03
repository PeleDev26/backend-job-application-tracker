package com.pelegrin.job_application_tracker.dto.user;

public record UpdateUserRequest(
        String firstName,
        String lastName,
        String email,
        String phone,
        Long roleId,
        Long cityId) {
}
