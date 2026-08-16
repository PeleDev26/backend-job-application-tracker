package com.pelegrin.job_application_tracker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "regions")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "nro_region", nullable = false, unique = true)
    private Integer nroRegion;

    @NotBlank
    @Column(name = "code_roman", nullable = false, unique = true)
    private String codeRoman;

    protected Region() {
    }

    public Region(String name, Integer nroRegion, String codeRoman) {
        this.name = name;
        this.nroRegion = nroRegion;
        this.codeRoman = codeRoman;
    }

}
