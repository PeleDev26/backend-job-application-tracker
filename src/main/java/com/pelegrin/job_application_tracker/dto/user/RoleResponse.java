package com.pelegrin.job_application_tracker.dto.user;

import java.util.List;

import lombok.Getter;

@Getter
public class RoleResponse {
    private Long id;
    private String name;
    private List<PermissionResponse> permissions;

}
