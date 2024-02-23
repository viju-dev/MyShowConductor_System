package com.example.MyShowConductor_System.Controllers;


import com.example.MyShowConductor_System.EntryDTOs.MovieEntryDTO;
import com.example.MyShowConductor_System.Entities.Movie;
import com.example.MyShowConductor_System.ResponseDTOs.MovieResponseDTO;
import com.example.MyShowConductor_System.Services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController //is used to create web services that return JSON or XML data
//@Controller //is used to create web controllers that return views, which is further resolved by view resolver
@Validated
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    MovieService movieService;

    @PostMapping("/add")
    public ResponseEntity addMovie(@RequestBody MovieEntryDTO movieEntryDTO){
        try {
            String result = movieService.addMovie(movieEntryDTO);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        catch (Exception e){
            String response = "Movie Not Added";
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/by-id") // this mapping name should be different for every get mapping
    public ResponseEntity getById(@RequestParam("id") @NotNull @Positive int id){
//        Movie movie = movieService.getById(id);  //try catch and through bad request if name or id is invalid / not exist

        //converted to movie to ResponseDto
        MovieResponseDTO movieResponseDTO = movieService.getById(id);
        return new ResponseEntity<>(movieResponseDTO,HttpStatus.OK);
    }
    @GetMapping("/by-name")
    public ResponseEntity getByName(@RequestParam("name") @NotBlank String name){
        MovieResponseDTO movieResponseDTO = movieService.getByName(name);
        return new ResponseEntity<>(movieResponseDTO,HttpStatus.OK);
    }
    @GetMapping("/by-languages")
    public ResponseEntity getByLanguages(@RequestParam("languages") @NotBlank String languages){
        List<MovieResponseDTO> dtoArrayList = movieService.getByLanguages(languages);

        return new ResponseEntity<>(dtoArrayList,HttpStatus.OK);
    }
    @GetMapping("/by-genre")
    public ResponseEntity getByGenre(@RequestParam("genres") @NotBlank String genres){ //list of enums?
        List<MovieResponseDTO> dtoArrayList = movieService.getByGenre(genres);
        return new ResponseEntity<>(dtoArrayList,HttpStatus.OK);
    }

    //theatre or movie Function // ig movies are 2d ,3d coz depends on how it shooted
    @GetMapping("/by-format") //by screenType // movie can be 3d but depends on show whether they are showing 3d or 2d so make that attribute in show as well
    public ResponseEntity getByFormat(@RequestParam("screenType") @NotNull List<String> screenType){

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity GetAll(){
        List<MovieResponseDTO> dtoArrayList = movieService.getAll();

        return new ResponseEntity<>(dtoArrayList,HttpStatus.OK);
    }
    @GetMapping("/top-movies")
    public ResponseEntity getTopMovie(){
        Movie movie = movieService.getTopMovie();
        return new ResponseEntity<>(movie,HttpStatus.OK);
    }
    @GetMapping("/max-shows")
    public ResponseEntity getByMaxShows(){
        String movieName = movieService.getByMaxShows();
        return new ResponseEntity<>(movieName,HttpStatus.OK);
    }
    @GetMapping("/collection")
    public ResponseEntity getCollectionByMovie(@RequestParam("movieName") @NotBlank String movieName){
        long totalCollecection = movieService.getCollectionByMovie(movieName);
        return new ResponseEntity<>( totalCollecection,HttpStatus.OK);
    }
    @PutMapping("/edit")
    public ResponseEntity editMovie(@RequestBody MovieEntryDTO movieEntryDTO){
        String result = movieService.editMovie(movieEntryDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/by-id")
    public ResponseEntity deleteById(@RequestParam("id") @NotNull @Positive int id){
        String result = movieService.deleteById(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @DeleteMapping("/by-name")
    public ResponseEntity deleteByName(@RequestParam("name") @NotBlank String name){
        String result = movieService.deleteByName(name);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @DeleteMapping("/all")
    public ResponseEntity deleteAll(){
        String result = movieService.deleteAll();
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    //format one and edit ones by name or something//


}
