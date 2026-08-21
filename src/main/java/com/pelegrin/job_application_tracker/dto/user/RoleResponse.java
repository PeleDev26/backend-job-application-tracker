package com.pelegrin.job_application_tracker.dto.user;

import java.util.List;

public record RoleResponse(
    Long id,
    String name,
    List<PermissionResponse> permissions
) {}
