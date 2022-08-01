package com.example.populationgrowthpredictor.client;

import com.example.populationgrowthpredictor.model.PopulationMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import java.util.Optional;

@Slf4j
@Component
public class PopulationRestClient {

    private final String BASE_URL = "https://population.un.org/dataportalapi/api/v1/";
    private final WebClient webClient;

    @Autowired
    public PopulationRestClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Optional<PopulationMessage> getPopulationStats(String uri) {
        try {
            ResponseEntity<PopulationMessage> response = webClient.get()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(PopulationMessage.class)
                    .block();

            if (response != null) {
                if (response.getStatusCode() == HttpStatus.OK) {
                    return Optional.ofNullable(response.getBody());
                } else {
                    log.warn("Unexpected status code retrieved when trying to fetch data {}", response.getStatusCode());
                }
            }
        } catch (WebClientException e) {
            log.error("Unable to fetch population stats", e);
        }
        return Optional.empty();
    }

}
