package com.example.populationgrowthpredictor.service;

import com.example.populationgrowthpredictor.client.PopulationRestClient;
import com.example.populationgrowthpredictor.model.Location;
import com.example.populationgrowthpredictor.model.PopulationMessage;
import com.example.populationgrowthpredictor.model.PopulationStats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.populationgrowthpredictor.service.LocationService.BASE_URL;

@Slf4j
@Service
public class PopulationService {


    private static final String VARIANT_TYPE = "Median";
    private static final String SEX_TYPE = "Both sexes";

    private final PopulationRestClient populationRestClient;

    private final LocationService locationService;

    @Autowired
    public PopulationService(PopulationRestClient populationRestClient, LocationService locationService) {
        this.populationRestClient = populationRestClient;
        this.locationService = locationService;
    }

    public List<PopulationStats> getExpectedPopulationByYear(int locationId, int year) {
        return this.getExpectedPopulationByStartYearAndEndYear(locationId, year, year);
    }

    public List<PopulationStats> getExpectedPopulationByStartYearAndEndYear(int locationId, int startYear, int endYear) {
        UriComponents url = UriComponentsBuilder
                .fromHttpUrl(BASE_URL)
                .path("/data/indicators/49/locations/{locationId}/start/{startYear}/end/{endYear}")
                .buildAndExpand(locationId, startYear, endYear);

        List<PopulationStats> result = new ArrayList<>();

        Optional<PopulationMessage> optionalPopulationMessage = populationRestClient.getPopulationStats(url.toUriString());
        if (optionalPopulationMessage.isPresent()) {
            PopulationMessage message = optionalPopulationMessage.get();
            var data = getData(message.getPopulationStats());
            if (!data.isEmpty()) {
                result.addAll(data);
            }
            while ((optionalPopulationMessage.isPresent() &&
                    optionalPopulationMessage.get().getNextPage() != null)) {

                optionalPopulationMessage = this.populationRestClient.getPopulationStats(message.getNextPage());
                if (optionalPopulationMessage.isPresent()) {
                    message = optionalPopulationMessage.get();
                    var extractedData = getData(message.getPopulationStats());
                    if (!extractedData.isEmpty()) {
                        result.addAll(extractedData);
                    }
                }
            }
        }

        return result;
    }

    public List<PopulationStats> getRangingByYear(int year) {
        List<Location> locationsDb = this.locationService.getAllLocations();
        List<PopulationStats> resultList = new ArrayList<>();
        for (Location location : locationsDb) {
            List<PopulationStats> listByLocation = this.getExpectedPopulationByYear(location.getId(), year);
            resultList.addAll(listByLocation);
        }

        return resultList.stream()
                .sorted((loc1, loc2) -> Long.compare(loc2.getValue(), loc1.getValue()))
                .limit(20)
                .collect(Collectors.toList());
    }

    private List<PopulationStats> getData(List<PopulationStats> populationStats) {
        List<PopulationStats> stats = new ArrayList<>();
        for (PopulationStats data : populationStats) {
            if (data.getVariant().equals(VARIANT_TYPE) && data.getSex().equals(SEX_TYPE)) {
                stats.add(data);
            }
        }
        return stats;
    }

}
