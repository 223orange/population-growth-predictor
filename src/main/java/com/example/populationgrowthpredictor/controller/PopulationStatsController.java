package com.example.populationgrowthpredictor.controller;

import com.example.populationgrowthpredictor.model.Location;
import com.example.populationgrowthpredictor.model.PopulationStats;
import com.example.populationgrowthpredictor.service.LocationService;
import com.example.populationgrowthpredictor.service.PopulationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Returns a list with stats about the population for a given location, start year and end year",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of population stats"),
                    @ApiResponse(responseCode = "404", description = "Not found"),
            })

    @Parameters({
        @Parameter(in = ParameterIn.PATH,
                description = "Location name",
                name = "locationName",
                content = @Content(schema = @Schema(type = "string", required = true))),
        @Parameter(in = ParameterIn.PATH,
                description = "Starting year",
                name = "startYear",
                content = @Content(schema = @Schema(type = "int", required = true))),
        @Parameter(in = ParameterIn.PATH,
                description = "Ending year",
                name = "endYear",
                content = @Content(schema = @Schema(type = "int", required = true)))
    })
    @GetMapping("/expected-population/{locationName}/{startYear}/{endYear}")
    public ResponseEntity<List<PopulationStats>> getExpectedPopulationByLocationStartYearAndEndYear(@PathVariable String locationName,
                                                                                                    @PathVariable int startYear,
                                                                                                    @PathVariable int endYear) {
        Optional<Location> optionalLocation = this.locationService.findLocationByName(locationName);
        if (optionalLocation.isPresent()) {
            Location savedLocation = optionalLocation.get();
            List<PopulationStats> response = this.populationService.getExpectedPopulationByStartYearAndEndYear(savedLocation.getId(), startYear, endYear);
            if (!response.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Returns a list with ranking of the top 20 countries, sorted by population for a given year",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of population stats"),
                    @ApiResponse(responseCode = "404", description = "Not found"),
            })

    @Parameters({
            @Parameter(in = ParameterIn.PATH,
                    description = "Given year",
                    name = "year",
                    content = @Content(schema = @Schema(type = "int", required = true)))
    })
    @GetMapping("/ranking/{year}")
    public ResponseEntity<List<PopulationStats>> getRankingByYear(@PathVariable int year) {
        List<PopulationStats> ranking = this.populationService.getRangingByYear(year);
        if (!ranking.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(ranking);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


}
