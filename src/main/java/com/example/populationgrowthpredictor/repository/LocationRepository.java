package com.example.populationgrowthpredictor.repository;

import com.example.populationgrowthpredictor.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    public Optional<Location> findByName(String name);

}
