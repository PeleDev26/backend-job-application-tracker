package com.pelegrin.job_application_tracker.service.users;

import java.util.List;
import org.springframework.stereotype.Service;
import com.pelegrin.job_application_tracker.dto.user.PermissionRequest;
import com.pelegrin.job_application_tracker.dto.user.PermissionResponse;
import com.pelegrin.job_application_tracker.entity.users.Permission;
import com.pelegrin.job_application_tracker.repository.users.PermissionRepository;
import com.pelegrin.job_application_tracker.validation.PermissionValidator;

@Service
public class PermissionService {

    private final PermissionRepository repository;
    private final PermissionValidator validator;

    public PermissionService(PermissionRepository repository, PermissionValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    // methods

    public PermissionResponse createPermission(PermissionRequest request) {

        validator.validateNameNotExists(request.name());

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

        Permission permission = validator.findByIdOrThrow(id);

        return new PermissionResponse(
                permission.getId(),
                permission.getName());
    }

    public void deletePermissionById(Long id) {
        validator.findByIdOrThrow(id);

        repository.deleteById(id);
    }

}
