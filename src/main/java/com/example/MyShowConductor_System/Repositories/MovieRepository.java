package com.example.MyShowConductor_System.Repositories;

import com.example.MyShowConductor_System.Entities.Language;
import com.example.MyShowConductor_System.Entities.Movie;
import com.example.MyShowConductor_System.Enums.FormatEnum;
import com.example.MyShowConductor_System.Enums.LanguagesEnum;
import com.example.MyShowConductor_System.Enums.MovieGenreEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie,Integer> {

//    Optional<Movie> findByTitle(String name);
    List<Movie> findByTitle(String name);


    void deleteByTitle(String name);

    List<Movie> findByGenresName(MovieGenreEnum name);
    Collection<? extends Movie> findByFormatsName(FormatEnum name);

    Collection<? extends Movie> findByLanguagesName(LanguagesEnum name);
    //Optional<Collection<? extends Movie>>

//    Collection<? extends Movie> findByFormatsName(FormatEnum name);


//    Movie findByName(String name);
}
