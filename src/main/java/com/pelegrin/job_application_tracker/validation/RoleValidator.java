package com.pelegrin.job_application_tracker.validation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.pelegrin.job_application_tracker.entity.users.Permission;
import com.pelegrin.job_application_tracker.entity.users.Role;
import com.pelegrin.job_application_tracker.exception.AppException;
import com.pelegrin.job_application_tracker.repository.users.RoleRepository;

@Component
public class RoleValidator {

    private final RoleRepository repository;

    public RoleValidator(RoleRepository repository) {
        this.repository = repository;
    }

    public Role findByIdOrThrow(Long id) {

        return repository.findById(id).orElseThrow(
                () -> new AppException(
                        HttpStatus.NOT_FOUND,
                        "Role not found"));
    }

    public void validatePermissionNotAssigned(Role role, Permission permission) {

        if (role.getPermissions().contains(permission)) {
            throw new AppException(
                    HttpStatus.CONFLICT,
                    "Permission already assigned to role");
        }
    }

    public void validatePermissionAssigned(Role role, Permission permission) {

        if (!role.getPermissions().contains(permission)) {
            throw new AppException(
                    HttpStatus.NOT_FOUND,
                    "Permission is not assigned to role");
        }
    }

}
