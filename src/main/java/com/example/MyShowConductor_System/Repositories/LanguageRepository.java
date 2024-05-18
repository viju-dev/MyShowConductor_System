package com.example.MyShowConductor_System.Repositories;

import com.example.MyShowConductor_System.Entities.Language;
import com.example.MyShowConductor_System.Enums.LanguagesEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language,Integer> {
    Optional<Language> findByName(LanguagesEnum name);
}
