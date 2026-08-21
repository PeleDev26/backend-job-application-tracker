package com.pelegrin.job_application_tracker.mapper.user;

import org.springframework.stereotype.Component;

import com.pelegrin.job_application_tracker.dto.user.PermissionResponse;
import com.pelegrin.job_application_tracker.dto.user.RoleResponse;
import com.pelegrin.job_application_tracker.entity.users.Role;

@Component
public class RoleMapper {

    public RoleResponse toResponse(Role role) {

        return new RoleResponse(
                role.getId(),
                role.getName(),
                role.getPermissions().stream().map(
                        permission -> new PermissionResponse(
                                permission.getId(),
                                permission.getName()))
                        .toList());
    }
    
}
