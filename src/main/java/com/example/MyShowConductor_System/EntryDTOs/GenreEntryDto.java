package com.example.MyShowConductor_System.EntryDTOs;

import com.example.MyShowConductor_System.Enums.MovieGenreEnum;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Data
public class GenreEntryDto {
    @NotNull(message = "movie genre cannot be null")
    @Enumerated(value = EnumType.STRING)
    private MovieGenreEnum name;
}
