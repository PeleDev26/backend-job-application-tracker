package com.pelegrin.job_application_tracker.repository;

import com.pelegrin.job_application_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByRut(String rut);

    boolean existsByEmail(String email);

    boolean existsByRut(String rut);
}
