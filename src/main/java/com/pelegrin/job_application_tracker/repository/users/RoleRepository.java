package com.pelegrin.job_application_tracker.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pelegrin.job_application_tracker.entity.users.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {}