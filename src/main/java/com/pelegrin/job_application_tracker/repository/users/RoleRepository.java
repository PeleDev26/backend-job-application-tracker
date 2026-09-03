package com.pelegrin.job_application_tracker.repository.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pelegrin.job_application_tracker.entity.users.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    Optional<Role> findByCode(String code);

    boolean existsByName(String name);
    
    boolean existsByCode(String code);
}