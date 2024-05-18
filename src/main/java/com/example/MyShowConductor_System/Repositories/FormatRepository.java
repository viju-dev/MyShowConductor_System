package com.example.MyShowConductor_System.Repositories;

import com.example.MyShowConductor_System.Entities.Format;
import com.example.MyShowConductor_System.Enums.FormatEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FormatRepository extends JpaRepository<Format,Integer> {
    Optional<Format> findByName(FormatEnum name);

//    Optional<Format> findByName(String formatName);
}
