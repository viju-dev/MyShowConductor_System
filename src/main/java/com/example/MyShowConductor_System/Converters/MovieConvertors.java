package com.example.MyShowConductor_System.Converters;

import com.example.MyShowConductor_System.EntryDTOs.MovieEntryDTO;
import com.example.MyShowConductor_System.Enums.LanguagesEnum;
import com.example.MyShowConductor_System.Enums.MovieGenreEnum;
import com.example.MyShowConductor_System.Entities.Movie;
import com.example.MyShowConductor_System.ResponseDTOs.MovieResponseDTO;

public class MovieConvertors {
    public static Movie convertEntryDtoToEntity(MovieEntryDTO movieEntryDTO){
//        LanguagesEnum[] languages = movieEntryDTO.getLanguages();

        //Languages Enum to String
        String lang ="";
        for (LanguagesEnum str:movieEntryDTO.getLanguages()){ //converting languageEnum to Language string
            lang += str.name()+",";
//            lang.concat(str.name()+",");
        }

        //Genres Enum to String
        String genre = "";
        for (MovieGenreEnum str:movieEntryDTO.getGenre()){ //converting MovieGenreEnum to genre string
            genre += str.name()+",";
        }

        Movie movie = Movie.builder().movieName(movieEntryDTO.getMovieName())
                .duration(movieEntryDTO.getDuration())
                .genre(genre)
                .rating(movieEntryDTO.getRating())
               .languages(lang)
                .build();
        return movie;
    }
    public static MovieResponseDTO convertDtoToResponse(Movie movie){
//        LanguagesEnum[] languages = movieEntryDTO.getLanguages();

        //Languages String to Enum
        String[] lang = movie.getLanguages().split(",");
        LanguagesEnum[] languages = new LanguagesEnum[lang.length];
        for (int i=0;i< lang.length;i++){
            languages[i]= LanguagesEnum.valueOf(lang[i]);
        }

        //Genres string to Enum
        String[] genre = movie.getGenre().split(",");
        MovieGenreEnum[] genres = new MovieGenreEnum[genre.length];
        for (int i=0;i< lang.length;i++){
            genres[i]= MovieGenreEnum.valueOf(genre[i]);
        }

        MovieResponseDTO movieResponseDTO = MovieResponseDTO.builder()
                .movieName(movie.getMovieName())
                .duration(movie.getDuration())
                .genre(genres)
                .languages(languages)
                .rating(movie.getRating()).build();
        return  movieResponseDTO;
    }
}
