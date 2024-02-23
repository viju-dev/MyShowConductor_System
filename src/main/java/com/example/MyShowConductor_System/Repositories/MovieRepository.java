package com.example.MyShowConductor_System.Repositories;

import com.example.MyShowConductor_System.Entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Integer> {

    Movie findByTitle(String name);

    List<Movie> findByLanguages(String languages);


    void deleteByTitle(String name);

//    Movie findByName(String name);
}
