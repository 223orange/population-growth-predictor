package com.example.populationgrowthpredictor.repository;

import com.example.populationgrowthpredictor.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

}
