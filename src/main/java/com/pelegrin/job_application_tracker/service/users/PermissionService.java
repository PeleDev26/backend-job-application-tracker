package com.pelegrin.job_application_tracker.service.users;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pelegrin.job_application_tracker.dto.user.PermissionRequest;
import com.pelegrin.job_application_tracker.dto.user.PermissionResponse;
import com.pelegrin.job_application_tracker.entity.users.Permission;
import com.pelegrin.job_application_tracker.exception.AppException;
import com.pelegrin.job_application_tracker.repository.users.PermissionRepository;

@Service
public class PermissionService {

    private final PermissionRepository repository;

    public PermissionService(PermissionRepository repository) {
        this.repository = repository;
    }

    // methods

    public PermissionResponse createPermission(PermissionRequest request) {

        if (repository.existsByName(request.name())) {
            throw new AppException(HttpStatus.CONFLICT, "Permission already exists");
        }

        Permission permission = new Permission(request.name());

        Permission saved = repository.save(permission);

        return new PermissionResponse(
                saved.getId(),
                saved.getName());
    }

    public List<PermissionResponse> getPermissions() {
        return repository.findAll().stream().map(permission -> new PermissionResponse(
                permission.getId(),
                permission.getName())).toList();
    }

    public PermissionResponse getPermissionById(Long id) {

        Permission permission = repository.findById(id).orElseThrow(
                () -> new AppException(
                        HttpStatus.NOT_FOUND,
                        "Permission not found"));

        return new PermissionResponse(
                permission.getId(),
                permission.getName());
    }

    public void deletePermissionById(Long id) {
        if (!repository.existsById(id)) {
            throw new AppException(
                    HttpStatus.NOT_FOUND,
                    "Permission does not exists");
        }

        repository.deleteById(id);
    }

}
