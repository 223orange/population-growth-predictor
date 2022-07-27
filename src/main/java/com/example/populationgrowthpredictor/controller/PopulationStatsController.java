package com.example.populationgrowthpredictor.controller;

import com.example.populationgrowthpredictor.model.LocationPopulationStats;
import com.example.populationgrowthpredictor.service.PopulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "population_predictor/api/v1")
public class PopulationStatsController {

    private final PopulationService populationService;

    @Autowired
    public PopulationStatsController(PopulationService populationService) {
        this.populationService = populationService;
    }

    @GetMapping("/expected-population/{location}/{startYear}/{endYear}")
    public ResponseEntity<List<LocationPopulationStats>> getExpectedPopulationByLocationStartYearAndEndYear(@PathVariable String location,
                                                                                                            @PathVariable int startYear,
                                                                                                            @PathVariable int endYear) {
        List<LocationPopulationStats> populationList = populationService.getExpectedPopulationByStartYearAndEndYear(location, startYear, endYear);
        if (!populationList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(populationList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
