package com.example.populationgrowthpredictor.controller;

import com.example.populationgrowthpredictor.model.Location;
import com.example.populationgrowthpredictor.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {
    private LocationService locationService;

    @Autowired
    public AdminController(LocationService locationService) {
        this.locationService = locationService;
    }

    @Operation(summary = "Populate DB with locations",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of locations")
            })
    @PostMapping(path = "/locations")
    public ResponseEntity<List<Location>> populateWithLocations(){
        return ResponseEntity.status(HttpStatus.OK).body(this.locationService.populateWithLocations());
    }
}
