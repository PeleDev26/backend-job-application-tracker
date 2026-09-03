package com.pelegrin.job_application_tracker.validation;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.pelegrin.job_application_tracker.dto.user.UpdateUserRequest;
import com.pelegrin.job_application_tracker.entity.City;
import com.pelegrin.job_application_tracker.entity.users.Role;
import com.pelegrin.job_application_tracker.entity.users.User;
import com.pelegrin.job_application_tracker.exception.AppException;
import com.pelegrin.job_application_tracker.repository.CityRepository;
import com.pelegrin.job_application_tracker.repository.users.UserRepository;

@Component
public class UserValidator {

    private final UserRepository userRepository;
    private final RoleValidator roleValidator;
    private final CityRepository cityRepository;

    public UserValidator(UserRepository userRepository,
            RoleValidator roleValidator,
            CityRepository cityRepository) {
        this.userRepository = userRepository;
        this.roleValidator = roleValidator;
        this.cityRepository = cityRepository;
    }

    public User findByIdOrThrow(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public void ensureEmailNotTaken(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new AppException(HttpStatus.CONFLICT, "Email already in use");
        }
    }

    public void ensureRutNotTaken(String rut) {
        if (userRepository.existsByRut(rut)) {
            throw new AppException(HttpStatus.CONFLICT, "RUT already in use");
        }
    }

    public Role findRoleByCodeOrThrow(String code) {
        return roleValidator.findByCodeOrThrow(code);
    }

    public Role findRoleOrThrow(Long id) {
        return roleValidator.findByIdOrThrow(id);
    }

    public City findCityOrThrow(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(
                        () -> new AppException(HttpStatus.NOT_FOUND, "City not found"));
    }

    public void ensureAtLeastOneFieldPresent(UpdateUserRequest req) {
        if (Stream.of(req.firstName(), req.lastName(), req.email(), req.phone(), req.roleId(), req.cityId())
                .allMatch(Objects::isNull)) {
            throw new AppException(HttpStatus.BAD_REQUEST, "No fields to update");
        }
    }

    public void ensureRoleChangeAllowed(UpdateUserRequest req, boolean allowRole) {
        if (!allowRole && req.roleId() != null) {
            throw new AppException(HttpStatus.FORBIDDEN, "Cannot change role");
        }
    }

    public void ensureEmailNotTakenForUpdate(String email, UUID currentUserId) {
        if (email == null)
            return;

        String normalized = email.trim().toLowerCase();
        userRepository.findByEmail(normalized).ifPresent(u -> {
            if (!u.getId().equals(currentUserId)) {
                throw new AppException(HttpStatus.CONFLICT, "Email already in use");
            }
        });
    }
}