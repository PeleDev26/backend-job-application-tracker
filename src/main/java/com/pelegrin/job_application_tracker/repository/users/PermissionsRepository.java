package com.pelegrin.job_application_tracker.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pelegrin.job_application_tracker.entity.users.Permission;

public interface PermissionsRepository extends JpaRepository<Permission, Long> {
}
