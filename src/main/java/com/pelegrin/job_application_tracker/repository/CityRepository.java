package com.pelegrin.job_application_tracker.repository;

import com.pelegrin.job_application_tracker.entity.City;
import com.pelegrin.job_application_tracker.entity.Region;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByNameAndRegion(String name, Region region);
}
