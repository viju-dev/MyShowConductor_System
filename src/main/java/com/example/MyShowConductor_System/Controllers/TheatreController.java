package com.example.MyShowConductor_System.Controllers;

import com.example.MyShowConductor_System.EntryDTOs.TheatreEntryDTO;
import com.example.MyShowConductor_System.ResponseDTOs.TheatreResponseDTO;
import com.example.MyShowConductor_System.ResponseDTOs.TheatreShowsResponnseDTO;
import com.example.MyShowConductor_System.Services.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Validated
@RequestMapping("/theatres")
public class TheatreController {
    @Autowired
    TheatreService theatreService;

    @PostMapping("/add")
    public ResponseEntity addTheatre(@RequestBody TheatreEntryDTO theatreEntryDTO){
        try {
            String result =  theatreService.addTheatre(theatreEntryDTO);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    //getTheatres with shows by location and movie
    @GetMapping("/by-location-and-movie")
    public ResponseEntity getAllByLocationAndMovie(@RequestParam("location") @NotBlank String location, @RequestParam("movieName") @NotBlank String movieName){
        List<TheatreShowsResponnseDTO> theatreList = theatreService.getAllByLocationAndMovie(location,movieName);
        return new ResponseEntity(theatreList,HttpStatus.OK);
    }

    //getAll theatres by location
    @GetMapping("/by-location")
    public ResponseEntity getAllByLocation(@RequestParam("location") @NotBlank String location){
        List<TheatreResponseDTO> theatreList = theatreService.getAllByLocation(location);
        return new ResponseEntity(theatreList,HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity getAll(){
        List<TheatreResponseDTO> theatreList = theatreService.getAll();
        return new ResponseEntity<>(theatreList,HttpStatus.OK);
    }

    @GetMapping("/by-movie")
    public ResponseEntity getAllByMovie(@RequestParam("movieName") @NotBlank String movieName){
        List<TheatreResponseDTO> theatreList = theatreService.getAllByMovie(movieName);
        return new ResponseEntity<>(theatreList,HttpStatus.OK);
    }

    @DeleteMapping("/by-id")
    public ResponseEntity deleteById(@RequestParam("theatreId") @NotNull @Positive int theatreId){
        String result = theatreService.deleteById(theatreId);
        return new ResponseEntity<>(result,HttpStatus.GONE);
    }
}
