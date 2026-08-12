package com.pelegrin.job_application_tracker.repository;

import com.pelegrin.job_application_tracker.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository  extends JpaRepository<Region, Long> {}
