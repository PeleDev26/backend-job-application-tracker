package com.pelegrin.job_application_tracker.entity.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
// import lombok.Setter;

@Getter
// @Setter
@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    protected Permission() {
    }

    public Permission(String name) {
        this.name = name;
    }

    // @Override
    // public boolean equals(Object o) {

    //     if (this == o) {
    //         return true;
    //     }

    //     if (!(o instanceof Permission other)) {
    //         return false;
    //     }

    //     return id != null && Objects.equals(id, other.id);
    // }

    // @Override
    // public int hashCode() {
    //     return getClass().hashCode();
    // }
}
