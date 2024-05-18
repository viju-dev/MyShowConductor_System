package com.example.MyShowConductor_System.Controllers;


import com.example.MyShowConductor_System.Entities.Format;
import com.example.MyShowConductor_System.Entities.Genre;
import com.example.MyShowConductor_System.Entities.Language;
import com.example.MyShowConductor_System.EntryDTOs.*;
import com.example.MyShowConductor_System.Entities.Movie;
import com.example.MyShowConductor_System.Enums.FormatEnum;
import com.example.MyShowConductor_System.Enums.LanguagesEnum;
import com.example.MyShowConductor_System.Enums.MovieGenreEnum;
import com.example.MyShowConductor_System.Payloads.ApiResponse;
import com.example.MyShowConductor_System.Payloads.ResponseData;
import com.example.MyShowConductor_System.ResponseDTOs.MovieResponseDTO;
import com.example.MyShowConductor_System.Services.Impl.MovieServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController //is used to create web services that return JSON or XML data
//@Controller //is used to create web controllers that return views, which is further resolved by view resolver
@Validated
@RequestMapping("/api")
public class MovieController {
    @Autowired
    MovieServiceImpl movieServiceImpl;

    @Autowired
    private ModelMapper modelMapper;
//    always use logger for any printing not sout
    @PostMapping("/movie/")
    public ResponseEntity<ApiResponse> addMovie(@RequestBody MovieEntryDTO movieEntryDTO){
        MovieResponseDTO savedMovie = movieServiceImpl.createMovie(movieEntryDTO);
        return new ResponseEntity<>(new ApiResponse<>("User Added Successfully",true,new ResponseData<>(savedMovie)),HttpStatus.CREATED);
    }

    @GetMapping("/movie/{movieId}") // this mapping name should be different for every get mapping
    public ResponseEntity getById(@PathVariable() @NotNull @Positive int movieId){
        MovieResponseDTO movie = movieServiceImpl.getMovieById(movieId);
        return new ResponseEntity<>(new ApiResponse<>("movie retrieved sucessfully",true,new ResponseData<>(movie)),HttpStatus.OK);
    }
    @GetMapping("/movie/name/{movieName}")
    public ResponseEntity getByName(@PathVariable() @NotBlank String movieName){
        List<MovieResponseDTO> movies = movieServiceImpl.getMoviesByName(movieName);
        return new ResponseEntity<>(new ApiResponse<>("movies retrieved sucessfully",true,new ResponseData(movies)),HttpStatus.OK);
    }
    @GetMapping("/movies/languages/")//location bhi chahiye // can add languages  list in url too
    public ResponseEntity getByLanguages(@RequestParam("languages")  List<LanguagesEnum> languages){
        List<MovieResponseDTO> movies = movieServiceImpl.getMoviesByLanguages(languages);
        return new ResponseEntity<>(new ApiResponse<>("movies retrieved successfully",true,new ResponseData(movies)),HttpStatus.OK);
    }
    @GetMapping("/movies/genres/")
    public ResponseEntity getByGenre(@RequestParam("genres") List<MovieGenreEnum> genres){ //list of enums?
//        List<GenreEntryDto> genres = genreList.getList();
        List<MovieResponseDTO> movies = movieServiceImpl.getMoviesByGenres(genres);
        return new ResponseEntity<>(new ApiResponse<>("movies retrieved successfully",true,new ResponseData(movies)),HttpStatus.OK);
    }

    //theatre or movie Function // ig movies are 2d ,3d coz depends on how it shooted/showed
    @GetMapping("/movies/formats/") //by screenType // movie can be 3d but depends on show whether they are showing 3d or 2d so make that attribute in show as well
    public ResponseEntity getByFormat(@RequestParam("formats") List<FormatEnum> formats){
        List<MovieResponseDTO> movies = movieServiceImpl.getMoviesByFormats(formats);
        return new ResponseEntity<>(new ApiResponse<>("movies retrieved successfully",true,new ResponseData(movies)),HttpStatus.OK);
    }

//    public ResponseEntity getByFormat(@RequestBody ListEntryDto<FormatEntryDto> formatList){
//        List<FormatEntryDto> formats = formatList.getList();
//        List<MovieResponseDTO> movies = movieServiceImpl.getMoviesByFormats(formats);
//        return new ResponseEntity<>(new ApiResponse<>("movies retrieved successfully",true,new ResponseData(movies)),HttpStatus.OK);
//    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/movies/")
    public ResponseEntity GetAll(){
        List<MovieResponseDTO> movies = movieServiceImpl.getAll();
        return new ResponseEntity<>(new ApiResponse<>("movies retrieved successfully",true,new ResponseData(movies)),HttpStatus.OK);
    }
    @GetMapping("/top-movies")
    public ResponseEntity getTopMovie(){
        List<MovieResponseDTO> movies = movieServiceImpl.getTopMovies();
        return new ResponseEntity<>(new ApiResponse<>("movies retrieved successfully",true,new ResponseData(movies)),HttpStatus.OK);
    }
    @GetMapping("/movies/max-shows")
    public ResponseEntity getByMaxShows(){
        List<MovieResponseDTO> movies = movieServiceImpl.getMovieByMaxShows();
        return new ResponseEntity<>(new ApiResponse<>("movies retrieved successfully",true,new ResponseData(movies)),HttpStatus.OK);
    }
    @GetMapping("/movies/{movieId}/collection")
    public ResponseEntity getCollectionByMovie(@PathVariable()  int movieId){
        long totalCollection = movieServiceImpl.getCollectionByMovie(movieId);
        return new ResponseEntity<>(new ApiResponse<>("Collection retrieved successfully",true,new ResponseData<>(totalCollection)),HttpStatus.OK);
    }
    @PutMapping("/movies/{movieId}")
    public ResponseEntity editMovie(@RequestBody MovieEntryDTO movieEntryDTO,@PathVariable int movieId){
        MovieResponseDTO movie = movieServiceImpl.updateMovie(movieEntryDTO,movieId);
        return new ResponseEntity<>(new ApiResponse<>("movie retrieved successfully",true,new ResponseData<>(movie)),HttpStatus.OK);
    }
    @DeleteMapping("/movies/{movieId}")
    public ResponseEntity deleteById(@PathVariable() @NotNull @Positive int movieId){
        String result = movieServiceImpl.deleteMovieById(movieId);
        return new ResponseEntity<>(new ApiResponse<>(result,true),HttpStatus.OK);

    }

    @DeleteMapping("/movies/")
    public ResponseEntity deleteAll(){
        String result = movieServiceImpl.deleteAll();
        return new ResponseEntity<>(new ApiResponse<>(result,true),HttpStatus.OK);
    }

    //format one and edit ones by name or something//


}
