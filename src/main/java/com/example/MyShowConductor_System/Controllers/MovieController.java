package com.example.MyShowConductor_System.Controllers;


import com.example.MyShowConductor_System.EntryDTOs.MovieEntryDTO;
import com.example.MyShowConductor_System.Entities.Movie;
import com.example.MyShowConductor_System.ResponseDTOs.MovieResponseDTO;
import com.example.MyShowConductor_System.Services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //is used to create web services that return JSON or XML data
//@Controller //is used to create web controllers that return views, which is further resolved by view resolver
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

    @GetMapping("/GetById") // this mapping name should be different for every get mapping
    public ResponseEntity GetById(@RequestParam("id") int id){
//        Movie movie = movieService.GetById(id);  //try catch and through bad request if name or id is invalis / not exist

        //converted to movie to ResponseDto
        MovieResponseDTO movieResponseDTO = movieService.GetById(id);
        return new ResponseEntity<>(movieResponseDTO,HttpStatus.FOUND);
    }
    @GetMapping("/GetByName")
    public ResponseEntity GetByName(@RequestParam("name") String name){
        MovieResponseDTO movieResponseDTO = movieService.GetByName(name);
        return new ResponseEntity<>(movieResponseDTO,HttpStatus.FOUND);
    }
    @GetMapping("/GetByLanguages")
    public ResponseEntity GetByLanguages(@RequestParam("languages") String languages){
        List<MovieResponseDTO> dtoArrayList = movieService.GetByLanguages(languages);

        return new ResponseEntity<>(dtoArrayList,HttpStatus.FOUND);
    }
    @GetMapping("/GetByGenre")
    public ResponseEntity GetByGenre(@RequestParam("genres") String genres){ //list of enums?
        List<MovieResponseDTO> dtoArrayList = movieService.GetByGenre(genres);
        return new ResponseEntity<>(dtoArrayList,HttpStatus.FOUND);
    }

    //theatre or movie Function // ig movies are 2d ,3d coz depends on how it shooted
    @GetMapping("/GetByFormat") //by screenType // movie can be 3d but depends on show whther they showing 3d or 2d so make that attribute in show as well
    public ResponseEntity GetByFormat(@RequestParam("screenType") List<String> screenType){

        return new ResponseEntity<>(HttpStatus.FOUND);
    }
    @GetMapping("/GetAll")
    public ResponseEntity GetAll(){
        List<MovieResponseDTO> dtoArrayList = movieService.GetAll();

        return new ResponseEntity<>(dtoArrayList,HttpStatus.FOUND);
    }
    @GetMapping("/GetTopMovie")
    public ResponseEntity GetTopMovie(){
        Movie movie = movieService.GetTopMovie();
        return new ResponseEntity<>(movie,HttpStatus.FOUND);
    }
    @GetMapping("/GetByMaxShows")
    public ResponseEntity GetByMaxShows(){
        String movieName = movieService.GetByMaxShows();
        return new ResponseEntity<>(movieName,HttpStatus.FOUND);
    }
    @GetMapping("/GetCollectionByMovie")
    public ResponseEntity GetCollectionByMovie(@RequestParam("movieName") String movieName){
        long totalCollecection = movieService.GetCollectionByMovie(movieName);
        return new ResponseEntity<>( totalCollecection,HttpStatus.FOUND);
    }
    @PutMapping("/EditMovie")
    public ResponseEntity EditMovie(@RequestBody MovieEntryDTO movieEntryDTO){
        String result = movieService.EditMovie(movieEntryDTO);
        return new ResponseEntity<>(HttpStatus.FOUND);
    }
    @DeleteMapping("/DeleteById")
    public ResponseEntity DeleteById(@RequestParam("id") int id){
        String result = movieService.DeleteById(id);
        return new ResponseEntity<>(result,HttpStatus.FOUND);
    }
    @DeleteMapping("/DeleteByName")
    public ResponseEntity DeleteByName(@RequestParam("name") String name){
        String result = movieService.DeleteByName(name);
        return new ResponseEntity<>(result,HttpStatus.FOUND);
    }
    @DeleteMapping("/DeleteAll")
    public ResponseEntity DeleteAll(){
        String result = movieService.DeleteAll();
        return new ResponseEntity<>(result,HttpStatus.FOUND);
    }

    //format one and edit ones by name or something//


}
