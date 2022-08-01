package com.example.populationgrowthpredictor.client;

import com.example.populationgrowthpredictor.model.LocationMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import java.util.Optional;

@Slf4j
@Component
public class LocationRestClient {
    private final WebClient webClient;


    @Autowired
    public LocationRestClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Optional<LocationMessage> getLocationPage(String url) {
        try {
            ResponseEntity<LocationMessage> response = webClient.get()
                    .uri(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(LocationMessage.class)
                    .block();
            if (response != null) {
                if (response.getStatusCode() == HttpStatus.OK) {
                    return Optional.ofNullable(response.getBody());
                } else {
                    log.warn("Unexpected status code retrieved when trying to fetch data {}", response.getStatusCode());
                }
            }
        } catch (WebClientException e) {
            log.error("Unable to fetch locations", e);
        }
        return Optional.empty();
    }
}
