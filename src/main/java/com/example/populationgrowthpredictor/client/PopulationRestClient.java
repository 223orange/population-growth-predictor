package com.example.populationgrowthpredictor.client;

import com.example.populationgrowthpredictor.model.LocationPopulationStats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class PopulationRestClient {

    private final String BASE_URL = "https://population.un.org/dataportalapi/api/v1/";
    private final WebClient webClient;

    @Autowired
    public PopulationRestClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<LocationPopulationStats> getPopulationByLocationStartYearEndYear(String location, int startYear, int endYear) {
        UriComponents url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("/data/indicators/49/locations/{location}/start/{startYear}/end/{endYear}")
                .buildAndExpand(location,
                        startYear, endYear);

        ResponseEntity<List<LocationPopulationStats>> response = webClient.get()
                .uri(url.toUriString())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<LocationPopulationStats>>() {
                }).block(Duration.ofSeconds(15));

        return response == null ? new ArrayList<>() : response.getBody();
    }

}
