package com.pelegrin.job_application_tracker.repository.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pelegrin.job_application_tracker.entity.users.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByName(String name);
    boolean existsByName(String name);
}
