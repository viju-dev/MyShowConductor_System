package com.example.MyShowConductor_System.Repositories;

import com.example.MyShowConductor_System.Entities.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show,Integer> {
    ArrayList<Show> findAllByMovieIdAndTheatreId(int movieId, int theatreId);

    ArrayList<Show> findAllByMovieId(int movieId);

    ArrayList<Show> findAllByMovieIdAndShowDate(int movieId, LocalDate showDate);


    ArrayList<Show> findAllByShowDate(LocalDate localDate); //Iterable<? extends Show>

    @Query(nativeQuery = true,value = "SELECT movie_id FROM bms_system.shows GROUP BY movie_id  ORDER BY COUNT(movie_id) DESC LIMIT 1")
    int getMovieByMax();

    List<Integer> findByShowDate(LocalDate localDate);

    ArrayList<Show> findAllByTheatreId(int theatreId);
}
