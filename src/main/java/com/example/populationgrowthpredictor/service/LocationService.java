package com.example.populationgrowthpredictor.service;

import com.example.populationgrowthpredictor.model.Location;
import com.example.populationgrowthpredictor.model.LocationMessage;
import com.example.populationgrowthpredictor.repository.LocationRepository;
import com.example.populationgrowthpredictor.client.LocationRestClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LocationService {
    public static final String BASE_URL = "https://population.un.org/dataportalapi/api/v1";

    private final LocationRestClient client;

    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRestClient client, LocationRepository locationRepository) {
        this.client = client;
        this.locationRepository = locationRepository;
    }

    public List<Location> populateWithLocations() {
        UriComponents url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/locations").build();
        Optional<LocationMessage> optionalLocationMessage = this.client.getLocationPage(url.toUriString());
        if (optionalLocationMessage.isPresent()) {
            LocationMessage message = optionalLocationMessage.get();
            List<Location> locations = new ArrayList<>(message.getLocations());
            do {
                optionalLocationMessage = this.client.getLocationPage(message.getNextPage());
                if (optionalLocationMessage.isPresent()){
                    message = optionalLocationMessage.get();
                    locations.addAll(message.getLocations());
                }
            } while (optionalLocationMessage.isPresent() && optionalLocationMessage.get().getNextPage() != null);
            return this.locationRepository.saveAll(locations);
        }
        return new ArrayList<>();
    }

    public List<Location> getAllLocations(){
        return this.locationRepository.findAll();
    }

    public Optional<Location> findLocationByName(String name){
        return this.locationRepository.findByName(name);
    }
}
