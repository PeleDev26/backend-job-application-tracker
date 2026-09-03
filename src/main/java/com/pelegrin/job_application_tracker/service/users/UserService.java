package com.pelegrin.job_application_tracker.service.users;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pelegrin.job_application_tracker.dto.user.CreateUserRequest;
import com.pelegrin.job_application_tracker.dto.user.UpdateUserRequest;
import com.pelegrin.job_application_tracker.dto.user.UserResponse;
import com.pelegrin.job_application_tracker.entity.City;
import com.pelegrin.job_application_tracker.entity.users.Role;
import com.pelegrin.job_application_tracker.entity.users.User;
import com.pelegrin.job_application_tracker.exception.AppException;
import com.pelegrin.job_application_tracker.mapper.user.UserMapper;
import com.pelegrin.job_application_tracker.repository.users.UserRepository;
import com.pelegrin.job_application_tracker.validation.UserValidator;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder,
            UserValidator userValidator, UserMapper mapper) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.mapper = mapper;
        this.encoder = encoder;
    }

    // ==========================================
    // register -
    // ==========================================

    @Transactional
    public UserResponse register(CreateUserRequest request) {
        userValidator.ensureEmailNotTaken(request.email());
        userValidator.ensureRutNotTaken(request.rut());

        Role role = userValidator.findRoleByCodeOrThrow("STANDARD");
        City city = userValidator.findCityOrThrow(request.cityId());

        User user = new User(
                request.rut(), request.firstName(),
                request.lastName(), null,
                request.email(), encoder.encode(request.password()),
                role, city);

        User saved = userRepository.save(user);
        return mapper.toResponse(saved);
    }

    // ==========================================
    // patchUserById -
    // ==========================================

    @Transactional
    public UserResponse patchUserById(UUID id, UpdateUserRequest req) {
        User user = userValidator.findByIdOrThrow(id);
        applyPatch(user, req, true);
        return mapper.toResponse(userRepository.save(user));
    }

    @Transactional
    public UserResponse updateCurrentUser(UUID id, UpdateUserRequest req) {
        User user = userValidator.findByIdOrThrow(id);
        applyPatch(user, req, false);
        return mapper.toResponse(userRepository.save(user));
    }

    // ==========================================
    // getAllUsers -
    // ==========================================

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(mapper::toResponse).toList();
    }

    // ==========================================
    // getUserById -
    // ==========================================

    public UserResponse getUserById(UUID id) {

        User user = userValidator.findByIdOrThrow(id);
        return mapper.toResponse(user);
    }

    // ==========================================
    // deletedUser -
    // ==========================================

    public void deletedUser(UUID id) {

        userValidator.findByIdOrThrow(id);

        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(HttpStatus.CONFLICT, "Message error integrity");
        }
    }

    private void applyPatch(User user, UpdateUserRequest req, boolean allowRole) {

        userValidator.ensureAtLeastOneFieldPresent(req);
        userValidator.ensureRoleChangeAllowed(req, allowRole);
        userValidator.ensureEmailNotTakenForUpdate(req.email(), user.getId());

        Role role = (req.roleId() != null && allowRole) ? userValidator.findRoleOrThrow(req.roleId()) : user.getRole();
        City city = req.cityId() != null ? userValidator.findCityOrThrow(req.cityId()) : user.getCity();

        user.updateInfoUser(
                req.firstName() != null ? req.firstName() : user.getFirstName(),
                req.lastName() != null ? req.lastName() : user.getLastName(),
                req.phone() != null ? req.phone() : user.getPhone(),
                req.email() != null ? req.email().trim().toLowerCase() : user.getEmail(),
                role, city);
    }

}
