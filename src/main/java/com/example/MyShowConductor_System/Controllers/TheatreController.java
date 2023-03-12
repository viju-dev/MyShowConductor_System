package com.example.MyShowConductor_System.Controllers;

import com.example.MyShowConductor_System.EntryDTOs.TheatreEntryDTO;
import com.example.MyShowConductor_System.ResponseDTOs.TheatreResponseDTO;
import com.example.MyShowConductor_System.ResponseDTOs.TheatreShowsResponnseDTO;
import com.example.MyShowConductor_System.Services.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    @GetMapping("/GetAllByLocationAndMovie")
    public ResponseEntity GetAllByLocationAndMovie(@RequestParam("location") String location,@RequestParam("movieName") String movieName){
        List<TheatreShowsResponnseDTO> theatreList = theatreService.GetAllByLocationAndMovie(location,movieName);
        return new ResponseEntity(theatreList,HttpStatus.FOUND);
    }

    //getAll theatres by location
    @GetMapping("/GetAllByLocation")
    public ResponseEntity GetAllByLocation(@RequestParam("location") String location){
        List<TheatreResponseDTO> theatreList = theatreService.GetAllByLocation(location);
        return new ResponseEntity(theatreList,HttpStatus.FOUND);
    }
    @GetMapping("/GetAll")
    public ResponseEntity GetAll(){
        List<TheatreResponseDTO> theatreList = theatreService.GetAll();
        return new ResponseEntity<>(theatreList,HttpStatus.FOUND);
    }

    @GetMapping("/GetAllByMovie")
    public ResponseEntity GetAllByMovie(@RequestParam("movieName") String movieName){
        List<TheatreResponseDTO> theatreList = theatreService.GetAllByMovie(movieName);
        return new ResponseEntity<>(theatreList,HttpStatus.FOUND);
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity deleteById(@RequestParam("theatreId") int theatreId){
        String result = theatreService.deleteById(theatreId);
        return new ResponseEntity<>(result,HttpStatus.GONE);
    }
}
