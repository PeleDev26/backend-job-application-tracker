package com.pelegrin.job_application_tracker.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class DtoRegion {
    
    @JsonProperty("region_name")
    private String regionName;
    
    @JsonProperty("normalized_name")
    private String normalizedName;

    @JsonProperty("nro_region")
    private Integer nroRegion;

    @JsonProperty("code_roman")
    private String codeRoman;

    private List<DtoCity> cities;

}