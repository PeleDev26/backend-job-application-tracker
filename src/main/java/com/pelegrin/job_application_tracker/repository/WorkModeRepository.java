package com.pelegrin.job_application_tracker.repository;

import com.pelegrin.job_application_tracker.entity.WorkMode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkModeRepository extends JpaRepository<WorkMode, Long> {}