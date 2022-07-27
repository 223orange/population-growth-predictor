package com.example.populationgrowthpredictor.service;

import com.example.populationgrowthpredictor.model.LocationPopulationStats;
import com.example.populationgrowthpredictor.client.PopulationRestClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PopulationService {
    private final PopulationRestClient populationRestClient;

    @Autowired
    public PopulationService(PopulationRestClient populationRestClient) {
        this.populationRestClient = populationRestClient;
    }

    public List<LocationPopulationStats> getExpectedPopulationByStartYearAndEndYear(String location, int startYear, int endYear){
        return populationRestClient.getPopulationByLocationStartYearEndYear(location, startYear, endYear);
    }
}
