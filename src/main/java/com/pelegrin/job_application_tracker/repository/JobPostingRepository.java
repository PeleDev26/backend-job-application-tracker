package com.pelegrin.job_application_tracker.repository;

import com.pelegrin.job_application_tracker.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
}