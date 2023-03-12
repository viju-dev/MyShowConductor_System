package com.example.MyShowConductor_System.Repositories;

import com.example.MyShowConductor_System.Entities.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheatreRepository extends JpaRepository<Theatre,Integer> {
    boolean existsByNameAndLocation(String name, String location);


    Theatre findByNameAndLocation(String theatreName, String location);

    Theatre[] findByLocation(String location);
}
