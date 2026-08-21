package com.pelegrin.job_application_tracker.validation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.pelegrin.job_application_tracker.dto.user.PermissionRequest;
import com.pelegrin.job_application_tracker.entity.users.Permission;
import com.pelegrin.job_application_tracker.exception.AppException;
import com.pelegrin.job_application_tracker.repository.users.PermissionRepository;

@Component
public class PermissionValidator {

    private final PermissionRepository repository;

    public PermissionValidator(PermissionRepository repository) {
        this.repository = repository;
    }

    public void validateNameNotExists(String name) {

        if (repository.existsByName(name)) {
            throw new AppException(
                    HttpStatus.CONFLICT,
                    "Permission already exists");
        }
    }

    public Permission findByIdOrThrow(Long id) {

        return repository.findById(id).orElseThrow(
                () -> new AppException(
                        HttpStatus.NOT_FOUND,
                        "Permission not found"));
    }

    public void validateNamesNotExists(List<PermissionRequest> requests) {

        Set<String> names = requests.stream()
                .map(PermissionRequest::name)
                .collect(Collectors.toSet());

        if (names.size() != requests.size()) {
            throw new AppException(
                    HttpStatus.CONFLICT,
                    "Duplicate permissions in request");
        }

        for (String name : names) {
            validateNameNotExists(name);
        }
    }
}
