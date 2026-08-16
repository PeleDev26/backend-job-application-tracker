package com.pelegrin.job_application_tracker.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "job_posting")
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active", nullable = false, columnDefinition = "boolean default true")
    private boolean isActive;

    @Column(name = "publication_date")
    private LocalDateTime publicationDate;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Lob
    @Column(name = "requirements", columnDefinition = "TEXT")
    private String requirements;

    @Column(name = "salary", precision = 12, scale = 2)
    private BigDecimal salary;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_mode_id", nullable = false)
    private WorkMode workMode;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private State status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @CreationTimestamp
    @Column(name = "created_at",nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected JobPosting() {
    }

    public JobPosting(String title, String description, LocalDateTime publicationDate,
            LocalDateTime expirationDate, String requirements, BigDecimal salary, WorkMode workMode, State status,
            Company company) {
        this.title = title;
        this.description = description;
        this.isActive = true;
        this.publicationDate = publicationDate;
        this.expirationDate = expirationDate;
        this.requirements = requirements;
        this.salary = salary;
        this.workMode = workMode;
        this.status = status;
        this.company = company;
    }
}