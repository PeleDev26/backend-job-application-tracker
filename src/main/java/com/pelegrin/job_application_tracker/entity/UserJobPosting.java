package com.pelegrin.job_application_tracker.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.pelegrin.job_application_tracker.entity.users.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_job_posting", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_job_posting", columnNames = { "user_id", "job_posting_id" })
})
public class UserJobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_posting_id", nullable = false)
    private JobPosting jobPosting;

    @CreationTimestamp
    @Column(name = "date_applied", nullable = false, updatable = false)
    private LocalDateTime dateApplied;

    protected UserJobPosting() {
    }

    public UserJobPosting(User user, JobPosting jobPosting) {
        this.user = user;
        this.jobPosting = jobPosting;
    }
}
