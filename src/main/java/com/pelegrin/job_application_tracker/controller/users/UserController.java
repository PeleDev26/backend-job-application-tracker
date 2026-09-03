package com.pelegrin.job_application_tracker.controller.users;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pelegrin.job_application_tracker.dto.ApiResponse;
import com.pelegrin.job_application_tracker.dto.user.CreateUserRequest;
import com.pelegrin.job_application_tracker.dto.user.UpdateUserRequest;
import com.pelegrin.job_application_tracker.dto.user.UserResponse;
import com.pelegrin.job_application_tracker.entity.users.User;
import com.pelegrin.job_application_tracker.service.users.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // ==========================================
    // ADMIN - Retrieve all users
    // ==========================================

    @GetMapping
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {

        return ResponseEntity.ok(
                ApiResponse.success(HttpStatus.OK.value(), "Users retrieved successfully",
                        service.getAllUsers()));
    }

    // ==========================================
    // PUBLIC - Register a new user
    // ==========================================

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @Valid @RequestBody CreateUserRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(HttpStatus.CREATED.value(), "User created successfully",
                                service.register(request)));
    }

    // ==========================================
    // AUTHENTICATED - Retrieve the current user's profile
    // ==========================================

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(
                ApiResponse.success(HttpStatus.OK.value(), "Current user retrieved successfully",
                        service.getUserById(user.getId())));
    }

    @PatchMapping("/me")
    @PreAuthorize("isAutenticated()")
    public ResponseEntity<ApiResponse<UserResponse>> updateCurrentUser(@Valid @RequestBody UpdateUserRequest request,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(
                ApiResponse.success(HttpStatus.OK.value(), "Current user patched succesfully",
                        service.updateCurrentUser(user.getId(), request)));
    }

    // ==========================================
    // ADMIN - Updated a user by Id
    // ==========================================

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public ResponseEntity<ApiResponse<UserResponse>> patchUserById(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(), "User patched successfully",
                        service.patchUserById(id, request)));
    }

    // ==========================================
    // ADMIN - Retrieve a user by Id
    // ==========================================

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable UUID id) {

        return ResponseEntity.ok(
                ApiResponse.success(HttpStatus.OK.value(), "User retrieved successfully",
                        service.getUserById(id)));
    }

    // ==========================================
    // ADMIN - Delete a user
    // ==========================================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable UUID id) {
        service.deletedUser(id);

        return ResponseEntity.ok(
                ApiResponse.success(HttpStatus.OK.value(), "User deleted successfully", null));
    }
}
