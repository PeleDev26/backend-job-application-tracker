package com.pelegrin.job_application_tracker.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pelegrin.job_application_tracker.entity.users.User;

import java.util.UUID;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByRut(String rut);

    boolean existsByEmail(String email);

    boolean existsByRut(String rut);
}
