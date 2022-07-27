package com.example.populationgrowthpredictor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationMessage {
    private Integer pageNumber;
    private Integer pageSize;
    private String nextPage;
    private Integer pages;
    @JsonProperty("data")
    private List<Location> locations;
}
