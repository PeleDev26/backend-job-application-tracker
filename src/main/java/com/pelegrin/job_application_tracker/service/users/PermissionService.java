package com.pelegrin.job_application_tracker.service.users;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.pelegrin.job_application_tracker.dto.user.PermissionRequest;
import com.pelegrin.job_application_tracker.dto.user.PermissionResponse;
import com.pelegrin.job_application_tracker.entity.users.Permission;
import com.pelegrin.job_application_tracker.exception.AppException;
import com.pelegrin.job_application_tracker.repository.users.PermissionRepository;
import com.pelegrin.job_application_tracker.validation.PermissionValidator;

import jakarta.transaction.Transactional;

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

    @Transactional
    public List<PermissionResponse> bulkCreatePermissions(List<PermissionRequest> requests) {

        validator.validateNamesNotExists(requests);

        List<Permission> permissions = requests.stream().map(
                request -> new Permission(request.name())).toList();

        return repository.saveAll(permissions)
                .stream().map(
                        permission -> new PermissionResponse(
                                permission.getId(),
                                permission.getName()))
                .toList();
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

        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(
                    HttpStatus.CONFLICT,
                    "Permission cannot be deleted because it is assigned to a role");
        }
    }

}
