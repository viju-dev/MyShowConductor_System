package com.example.MyShowConductor_System.EntryDTOs;

import com.example.MyShowConductor_System.Entities.Format;
import com.example.MyShowConductor_System.Entities.Genre;
import com.example.MyShowConductor_System.Entities.Language;
import com.example.MyShowConductor_System.Enums.LanguagesEnum;
import com.example.MyShowConductor_System.Enums.MovieGenreEnum;
import com.example.MyShowConductor_System.Enums.FormatEnum;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.util.List;

@Data

public class MovieEntryDTO {

    @NotEmpty(message = "movie name cannot be empty or null")
    private String title;

//    initially lets make it null or lets not use with dtoo and allow sepparate method to rating
//    @Positive(message = "movie rating value should be between 0 to 10")
//    @Max(value = 10,message = "rating value should be between 0 to 10")
//    private Double rating; // question to create rating before or not and if created what should be the vlaue
                           //second question should we allow users to let rating before watching movies ? or just give deafult to be 10 for initial one

    @NotNull(message = "movie genre cannot be null")
    @Enumerated(value = EnumType.STRING)
    private List<GenreEntryDto> genres;

    @NotNull(message = "movie format cannot be null")
    @Enumerated(value = EnumType.STRING)
    private List<FormatEntryDto> formats;

    @Positive(message = "movie duration can't be less than 1 minute")
    @Min(value = 1,message = "duration can't be less than 1 minute")
    private int duration;//in mins

//    private List<LanguagesEnum> languages;
//    private LanguagesEnum languages;
    @NotNull(message = "movie supported languages can't be null")
    @Enumerated(value = EnumType.STRING)
    private List<LanguageEntryDto> languages; // to get correct multiple languages used in entryDto
//    private List<String> languages;
    //LanguagesEnum
}
