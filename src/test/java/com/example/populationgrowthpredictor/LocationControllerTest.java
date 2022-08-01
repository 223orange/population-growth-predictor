package com.example.populationgrowthpredictor;

import com.example.populationgrowthpredictor.controller.AdminController;
import com.example.populationgrowthpredictor.model.Location;
import com.example.populationgrowthpredictor.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class LocationControllerTest {

    private static final String LOCATIONS_DIR = "/admin/locations";

    @Mock
    private LocationService locationService;

    @InjectMocks
    AdminController adminController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void populateLocations_shouldReturnOK() throws Exception {
        Location location = Location.builder()
                .name("France")
                .id(250)
                .build();

        Mockito.when(this.locationService.populateWithLocations()).thenReturn(List.of(location));

        mockMvc.perform(post(LOCATIONS_DIR))
                .andExpect(status().isOk());
    }

    @Test
    void populateLocationsWhichAlreadyExist_shouldReturnOK() throws Exception {
        Mockito.when(this.locationService.populateWithLocations()).thenReturn(new ArrayList<>());

        mockMvc.perform(post(LOCATIONS_DIR))
                .andExpect(status().isOk());
    }
}
