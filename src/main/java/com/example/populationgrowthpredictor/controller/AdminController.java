package com.example.populationgrowthpredictor.controller;

import com.example.populationgrowthpredictor.model.Location;
import com.example.populationgrowthpredictor.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(path = "/locations")
    public ResponseEntity<List<Location>> populateWithLocations(){
        List<Location> result = locationService.populateWithLocations();
        if( !result.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
