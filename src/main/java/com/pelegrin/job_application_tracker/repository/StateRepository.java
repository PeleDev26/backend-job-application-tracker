package com.pelegrin.job_application_tracker.repository;

import com.pelegrin.job_application_tracker.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {}