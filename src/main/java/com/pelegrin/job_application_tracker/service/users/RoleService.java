package com.pelegrin.job_application_tracker.service.users;

import com.pelegrin.job_application_tracker.entity.users.Permission;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.pelegrin.job_application_tracker.dto.user.RoleRequest;
import com.pelegrin.job_application_tracker.dto.user.RoleResponse;
import com.pelegrin.job_application_tracker.entity.users.Role;
import com.pelegrin.job_application_tracker.exception.AppException;
import com.pelegrin.job_application_tracker.mapper.user.RoleMapper;
import com.pelegrin.job_application_tracker.repository.users.RoleRepository;
import com.pelegrin.job_application_tracker.validation.PermissionValidator;
import com.pelegrin.job_application_tracker.validation.RoleValidator;

import jakarta.transaction.Transactional;

@Service
public class RoleService {

    private final RoleRepository repository;
    private final RoleValidator validator;
    private final PermissionValidator permissionValidator;
    private final RoleMapper mapper;

    public RoleService(RoleRepository repository, RoleValidator validator, PermissionValidator permissionValidator,
            RoleMapper mapper) {
        this.repository = repository;
        this.validator = validator;
        this.permissionValidator = permissionValidator;
        this.mapper = mapper;

    }

    public RoleResponse createRole(RoleRequest request) {
        validator.validateNameNotExists(request.name());
        validator.validateCodeNotExists(request.code());

        Role role = new Role(request.code(), request.name());
        Role saved = repository.save(role);

        return mapper.toResponse(saved);
    }

    public List<RoleResponse> getAllRoles() {

        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    public RoleResponse getRoleById(Long id) {

        Role role = validator.findByIdOrThrow(id);

        return mapper.toResponse(role);
    }

    public void deleteRoleById(Long id) {
        validator.findByIdOrThrow(id);

        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(HttpStatus.CONFLICT, "Role cannot be deleted because it is assigned to a user");
        }
    }

    @Transactional
    public RoleResponse addPermissionToRole(Long roleId, Long permissionId) {

        Role role = validator.findByIdOrThrow(roleId);
        Permission permission = permissionValidator.findByIdOrThrow(permissionId);

        validator.validatePermissionNotAssigned(role, permission);
        role.addPermission(permission);

        return mapper.toResponse(role);
    }

    @Transactional
    public RoleResponse removePermissionFromRole(Long roleId, Long permissionId) {

        Role role = validator.findByIdOrThrow(roleId);
        Permission permission = permissionValidator.findByIdOrThrow(permissionId);

        validator.validatePermissionAssigned(role, permission);
        role.removePermission(permission);

        return mapper.toResponse(role);
    }

}
