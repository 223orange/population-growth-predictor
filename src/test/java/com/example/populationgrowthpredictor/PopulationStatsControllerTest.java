package com.example.populationgrowthpredictor;

import com.example.populationgrowthpredictor.controller.PopulationStatsController;
import com.example.populationgrowthpredictor.model.Location;
import com.example.populationgrowthpredictor.model.PopulationStats;
import com.example.populationgrowthpredictor.service.LocationService;
import com.example.populationgrowthpredictor.service.PopulationService;
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

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PopulationStatsControllerTest {
    private static final String BASE_DIR = "/population_predictor/api/v1";
    private static final String POPULATION_COUNT = "/expected-population/{locationName}/{startYear}/{endYear}";

    @Mock
    private PopulationService populationService;

    @Mock
    private LocationService locationService;

    @InjectMocks
    PopulationStatsController populationStatsController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(populationStatsController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void getExpectedPopulationForAGivenLocation_shouldReturnResult() throws Exception {
        Location location = Location.builder()
                .name("France")
                .id(250)
                .build();


        Mockito.when(locationService.findLocationByName("France")).thenReturn(Optional.of(location));
        Mockito.when(populationService.getExpectedPopulationByStartYearAndEndYear(anyInt(), anyInt(), anyInt()))
                .thenReturn(List.of(createPopulationStats()));

        mockMvc.perform(get(BASE_DIR + POPULATION_COUNT, "France", "2035", "2036"))
                .andExpect(status().isOk());
    }


    @Test
    void getExpectedPopulationForANotExistingLocation_shouldReturnNotFound() throws Exception {
        Location location = Location.builder()
                .name("France")
                .id(250)
                .build();


        Mockito.when(locationService.findLocationByName("France")).thenReturn(Optional.empty());;

        mockMvc.perform(get(BASE_DIR + POPULATION_COUNT, "France", "2035", "2036"))
                .andExpect(status().isNotFound());
    }

    private PopulationStats createPopulationStats() {
        return PopulationStats.builder()
                .locationId(250)
                .location("France")
                .time("2035")
                .variant("Median")
                .value(10315L)
                .build();
    }


}
