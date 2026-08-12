package com.pelegrin.job_application_tracker.repository;

import com.pelegrin.job_application_tracker.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {}