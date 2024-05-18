package com.example.MyShowConductor_System.Repositories;

import com.example.MyShowConductor_System.Entities.Genre;
import com.example.MyShowConductor_System.Enums.FormatEnum;
import com.example.MyShowConductor_System.Enums.MovieGenreEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre,Integer> {
    Optional<Genre> findByName(MovieGenreEnum name);
}
