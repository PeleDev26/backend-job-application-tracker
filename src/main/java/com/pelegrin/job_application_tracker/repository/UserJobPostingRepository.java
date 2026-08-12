package com.pelegrin.job_application_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pelegrin.job_application_tracker.entity.UserJobPosting;
import java.util.UUID;

public interface UserJobPostingRepository extends JpaRepository<UserJobPosting, Long> {
    boolean existsByUserIdAndJobPostingId(UUID userId, Long jobPostingId);
}
