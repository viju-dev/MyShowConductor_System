package com.example.MyShowConductor_System.Controllers;


import com.example.MyShowConductor_System.EntryDTOs.*;
import com.example.MyShowConductor_System.Enums.FormatEnum;
import com.example.MyShowConductor_System.Enums.LanguagesEnum;
import com.example.MyShowConductor_System.Enums.MovieGenreEnum;
import com.example.MyShowConductor_System.Payloads.ApiResponse;
import com.example.MyShowConductor_System.Payloads.ResponseData;
import com.example.MyShowConductor_System.ResponseDTOs.MovieResponseDTO;
import com.example.MyShowConductor_System.Services.MovieService;
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

@RestController
@Validated
@RequestMapping("/api")
public class MovieController {
    @Autowired
    MovieService movieService;

    @Autowired
    private ModelMapper modelMapper;
//    always use logger for any printing not sout

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/movie/")
    public ResponseEntity<ApiResponse> addMovie(@RequestBody MovieEntryDTO movieEntryDTO){
        MovieResponseDTO savedMovie = movieService.createMovie(movieEntryDTO);
        return new ResponseEntity<>(new ApiResponse<>("User Added Successfully",true,new ResponseData<>(savedMovie)),HttpStatus.CREATED);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity getById(@PathVariable() @NotNull @Positive int movieId){
        MovieResponseDTO movie = movieService.getMovieById(movieId);
        return new ResponseEntity<>(new ApiResponse<>("movie retrieved sucessfully",true,new ResponseData<>(movie)),HttpStatus.OK);
    }
    @GetMapping("/movie/name/{movieName}")
    public ResponseEntity getByName(@PathVariable() @NotBlank String movieName){
        List<MovieResponseDTO> movies = movieService.getMoviesByName(movieName);
        return new ResponseEntity<>(new ApiResponse<>("movies retrieved sucessfully",true,new ResponseData(movies)),HttpStatus.OK);
    }
    @GetMapping("/movies/languages/")
    public ResponseEntity getByLanguages(@RequestParam("languages")  List<LanguagesEnum> languages){
        List<MovieResponseDTO> movies = movieService.getMoviesByLanguages(languages);
        return new ResponseEntity<>(new ApiResponse<>("movies retrieved successfully",true,new ResponseData(movies)),HttpStatus.OK);
    }
    @GetMapping("/movies/genres/")
    public ResponseEntity getByGenre(@RequestParam("genres") List<MovieGenreEnum> genres){
        List<MovieResponseDTO> movies = movieService.getMoviesByGenres(genres);
        return new ResponseEntity<>(new ApiResponse<>("movies retrieved successfully",true,new ResponseData(movies)),HttpStatus.OK);
    }

    @GetMapping("/movies/formats/")
    public ResponseEntity getByFormat(@RequestParam("formats") List<FormatEnum> formats){
        List<MovieResponseDTO> movies = movieService.getMoviesByFormats(formats);
        return new ResponseEntity<>(new ApiResponse<>("movies retrieved successfully",true,new ResponseData(movies)),HttpStatus.OK);
    }



    @GetMapping("/movies/")
    public ResponseEntity GetAll(){
        List<MovieResponseDTO> movies = movieService.getAll();
        return new ResponseEntity<>(new ApiResponse<>("movies retrieved successfully",true,new ResponseData(movies)),HttpStatus.OK);
    }
    @GetMapping("/top-movies")
    public ResponseEntity getTopMovie(){
        List<MovieResponseDTO> movies = movieService.getTopMovies();
        return new ResponseEntity<>(new ApiResponse<>("movies retrieved successfully",true,new ResponseData(movies)),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/movies/max-shows")
    public ResponseEntity getByMaxShows(){
        List<MovieResponseDTO> movies = movieService.getMovieByMaxShows();
        return new ResponseEntity<>(new ApiResponse<>("movies retrieved successfully",true,new ResponseData(movies)),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/movies/{movieId}/collection")
    public ResponseEntity getCollectionByMovie(@PathVariable()  int movieId){
        long totalCollection = movieService.getCollectionByMovie(movieId);
        return new ResponseEntity<>(new ApiResponse<>("Collection retrieved successfully",true,new ResponseData<>(totalCollection)),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/movies/{movieId}")
    public ResponseEntity editMovie(@RequestBody MovieEntryDTO movieEntryDTO,@PathVariable int movieId){
        MovieResponseDTO movie = movieService.updateMovie(movieEntryDTO,movieId);
        return new ResponseEntity<>(new ApiResponse<>("movie retrieved successfully",true,new ResponseData<>(movie)),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/movies/{movieId}")
    public ResponseEntity deleteById(@PathVariable() @NotNull @Positive int movieId){
        String result = movieService.deleteMovieById(movieId);
        return new ResponseEntity<>(new ApiResponse<>(result,true),HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/movies/")
    public ResponseEntity deleteAll(){
        String result = movieService.deleteAll();
        return new ResponseEntity<>(new ApiResponse<>(result,true),HttpStatus.OK);
    }

    //format one and edit ones by name or something//


}
