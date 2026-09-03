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
import com.pelegrin.job_application_tracker.dto.user.PermissionRequest;
import com.pelegrin.job_application_tracker.dto.user.PermissionResponse;
import com.pelegrin.job_application_tracker.service.users.PermissionService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    private final PermissionService service;

    public PermissionController(PermissionService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    public ResponseEntity<ApiResponse<List<PermissionResponse>>> getAllPermissions() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Permissions retrieved successfully",
                        service.getPermissions()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    public ResponseEntity<ApiResponse<PermissionResponse>> getPermissionById(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Permission retrieved successfully",
                        service.getPermissionById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PERMISSION_CREATE')")
    public ResponseEntity<ApiResponse<PermissionResponse>> createPermission(
            @Valid @RequestBody PermissionRequest request) {

        // System.out.println("POST /permissions -> " + request.name());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                HttpStatus.CREATED.value(),
                                "Permission created successfully",
                                service.createPermission(request)));
    }

    @PostMapping("/bulk")
    @PreAuthorize("hasAuthority('PERMISSION_CREATE')")
    public ResponseEntity<ApiResponse<List<PermissionResponse>>> bulkCreatePermissions(
            @RequestBody @NotEmpty List<@Valid PermissionRequest> requests) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                HttpStatus.CREATED.value(),
                                "Permissions created succesfully",
                                service.bulkCreatePermissions(requests)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_DELETE')")
    public ResponseEntity<ApiResponse<Void>> deletePermission(@PathVariable Long id) {

        service.deletePermissionById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Permission deleted successfully",
                        null));
    }

}