package com.example.populationgrowthpredictor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PopulationMessage {
    private Integer pageNumber;
    private Integer pageSize;
    private String nextPage;
    private Integer pages;
//    @JsonProperty("data")
//    private List<> locations;
}
