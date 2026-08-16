package com.pelegrin.job_application_tracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class DtoCity {

    private String name;
    
    @JsonProperty("normalized_name")
    private String normalizedName;

}
