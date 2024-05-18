package com.example.MyShowConductor_System.ResponseDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponseDTO {
//    private String movieName;
//    private Double rating;
//    private MovieGenreEnum[] genre; // can be changed to string as we are just showing it to user
////private String genre;
//    private List<Format> formats;
//    private int duration;
//    private LanguagesEnum[] languages;
////private String languages;

    private int id;
    private String title;
    private Double rating;
    private int duration;
    private List<GenreResponseDto> genres; // can be changed to string as we are just showing it to user
    //private String genre;
    private List<FormatResponseDto> formats;
    private List<LanguageResponseDto> languages;
//private String languages;
}
