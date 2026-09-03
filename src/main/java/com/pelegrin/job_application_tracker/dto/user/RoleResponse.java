package com.pelegrin.job_application_tracker.dto.user;

import java.util.List;

public record RoleResponse(
    Long id,
    String code,
    String name,
    List<PermissionResponse> permissions
) {}
