package com.example.populationgrowthpredictor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PopulationStats {

    private Integer locationId;

    private String location;

    @JsonProperty("timeLabel")
    private String time;

    private String variant;

    private String sex;

    private Long value;
}
