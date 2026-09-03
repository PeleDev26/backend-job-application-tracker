package com.pelegrin.job_application_tracker.controller.users;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pelegrin.job_application_tracker.dto.ApiResponse;
import com.pelegrin.job_application_tracker.dto.user.RoleRequest;
import com.pelegrin.job_application_tracker.dto.user.RoleResponse;
import com.pelegrin.job_application_tracker.service.users.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    // ==========================================
    // ADMIN -
    // ==========================================

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAllRoles() {

        return ResponseEntity.ok(
                ApiResponse.success(HttpStatus.OK.value(), "Roles retrieved successfully",
                        service.getAllRoles()));
    }

    // ==========================================
    // ADMIN -
    // ==========================================

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public ResponseEntity<ApiResponse<RoleResponse>> getRoleById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(HttpStatus.OK.value(), "Role retrieved successfully",
                        service.getRoleById(id)));
    }

    // ==========================================
    // ADMIN -
    // ==========================================

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(
            @Valid @RequestBody RoleRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                HttpStatus.CREATED.value(),
                                "Role created successfully",
                                service.createRole(request)));
    }

    // ==========================================
    // ADMIN -
    // ==========================================

    @PostMapping("/{roleId}/permissions/{permissionId}")
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    public ResponseEntity<ApiResponse<RoleResponse>> addPermissionToRole(@PathVariable Long roleId,
            @PathVariable Long permissionId) {

        return ResponseEntity.ok(
                ApiResponse.success(HttpStatus.OK.value(), "Permission added to role successfully",
                        service.addPermissionToRole(roleId, permissionId)));
    }

    // ==========================================
    // ADMIN -
    // ==========================================

    @DeleteMapping("/{roleId}/permissions/{permissionId}")
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    public ResponseEntity<ApiResponse<RoleResponse>> removePermissionFromRole(
            @PathVariable Long roleId,
            @PathVariable Long permissionId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Permission removed from role successfully",
                        service.removePermissionFromRole(roleId, permissionId)));
    }

    // ==========================================
    // ADMIN -
    // ==========================================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    public ResponseEntity<ApiResponse<Void>> deleteRoleById(
            @PathVariable Long id) {

        service.deleteRoleById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Role deleted successfully",
                        null));
    }
}