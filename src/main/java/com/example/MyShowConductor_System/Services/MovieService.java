package com.example.MyShowConductor_System.Services;

import com.example.MyShowConductor_System.Entities.Format;
import com.example.MyShowConductor_System.Entities.Genre;
import com.example.MyShowConductor_System.Entities.Language;
import com.example.MyShowConductor_System.EntryDTOs.FormatEntryDto;
import com.example.MyShowConductor_System.EntryDTOs.GenreEntryDto;
import com.example.MyShowConductor_System.EntryDTOs.LanguageEntryDto;
import com.example.MyShowConductor_System.EntryDTOs.MovieEntryDTO;
import com.example.MyShowConductor_System.Enums.FormatEnum;
import com.example.MyShowConductor_System.Enums.LanguagesEnum;
import com.example.MyShowConductor_System.Enums.MovieGenreEnum;
import com.example.MyShowConductor_System.ResponseDTOs.MovieResponseDTO;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Set;

public interface MovieService {
//    basic methods
     MovieResponseDTO createMovie(MovieEntryDTO movieEntryDTO) ;

     MovieResponseDTO updateMovie(MovieEntryDTO movieEntryDTO, int movieId) ;

     MovieResponseDTO getMovieById(int movieId) ;

    public List<MovieResponseDTO> getAll();

    public String deleteMovieById(int id);
    //    public String deleteByName(String name); // as their might be multiple movies with same name
    public String deleteAll();


//    additional methods
    public List<MovieResponseDTO> getMoviesByName(String name);
    public List<MovieResponseDTO> getMoviesByLanguages(List<LanguagesEnum> languages);
    public List<MovieResponseDTO> getMoviesByGenres(List<MovieGenreEnum> genres);

    public List<MovieResponseDTO> getMoviesByFormats( List<FormatEnum> formats);
    public List<MovieResponseDTO> getTopMovies();//toprated movie or mayble top_5_movies() // or maybe whose rating greater than 9
    public List<MovieResponseDTO> getMovieByMaxShows(); // we'll return dto instead of string

    public long getCollectionByMovie(int movieId); // we'll return dto instead of string'


//    different methods
    public void sendMail(String email,String text,String subject) throws MessagingException;


//    additional methods that fullfill attribute absense in response
//    public List<Show> getShowsByMovie(int movieId);

}
