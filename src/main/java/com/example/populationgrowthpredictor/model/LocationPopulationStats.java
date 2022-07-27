package com.example.populationgrowthpredictor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationPopulationStats {

    @NotNull
    private Integer id;
    @NotNull
    private String locationId;
    @NotNull
    private String location;
    @NotNull
    @JsonProperty("timeLabel")
    private Integer year;
    @NotNull
    private String variant;
    @NotNull
    private String sex;
    @NotNull
    private Long value;
}
