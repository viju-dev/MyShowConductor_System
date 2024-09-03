package com.example.MyShowConductor_System.EntryDTOs;

import com.example.MyShowConductor_System.Enums.MovieGenreEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreEntryDto {
    @NotNull(message = "movie genre cannot be null")
    @Enumerated(value = EnumType.STRING)
    private MovieGenreEnum name;
}
