package com.pelegrin.job_application_tracker.repository;

import com.pelegrin.job_application_tracker.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByName(String name);

    boolean existsByName(String name);

    boolean existsByWebsite(String website);
}