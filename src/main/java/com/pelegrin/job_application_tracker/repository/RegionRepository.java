package com.pelegrin.job_application_tracker.repository;

import com.pelegrin.job_application_tracker.entity.Region;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository  extends JpaRepository<Region, Long> {
    Optional<Region> findByNroRegion(Integer nroRegion);
}
