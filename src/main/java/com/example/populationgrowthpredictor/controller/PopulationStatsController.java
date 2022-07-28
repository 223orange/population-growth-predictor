package com.example.populationgrowthpredictor.controller;

import com.example.populationgrowthpredictor.model.Location;
import com.example.populationgrowthpredictor.model.LocationPopulationStats;
import com.example.populationgrowthpredictor.service.LocationService;
import com.example.populationgrowthpredictor.service.PopulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "population_predictor/api/v1")
public class PopulationStatsController {

    private final PopulationService populationService;

    private final LocationService locationService;

    @Autowired
    public PopulationStatsController(PopulationService populationService, LocationService locationService) {
        this.populationService = populationService;
        this.locationService = locationService;
    }

    @GetMapping("/expected-population/{locationName}/{startYear}/{endYear}")
    public ResponseEntity<List<LocationPopulationStats>> getExpectedPopulationByLocationStartYearAndEndYear(@PathVariable String locationName,
                                                                                                            @PathVariable int startYear,
                                                                                                            @PathVariable int endYear) {
        Optional<Location> optionalLocation = this.locationService.findLocationByName(locationName);
        if(optionalLocation.isPresent()){
            Location savedLocation = optionalLocation.get();
            List<LocationPopulationStats> populationList = populationService.getExpectedPopulationByStartYearAndEndYear(savedLocation.getId(), startYear, endYear);
            if (!populationList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(populationList);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
