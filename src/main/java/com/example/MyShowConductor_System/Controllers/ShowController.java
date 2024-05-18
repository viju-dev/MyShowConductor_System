package com.example.MyShowConductor_System.Controllers;

import com.example.MyShowConductor_System.EntryDTOs.ShowEntryDTO;
import com.example.MyShowConductor_System.Payloads.ApiResponse;
import com.example.MyShowConductor_System.Payloads.ResponseData;
import com.example.MyShowConductor_System.ResponseDTOs.ShowResponseDTO;
import com.example.MyShowConductor_System.Services.Impl.ShowServiceImpl;
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
@RequestMapping("/api/shows")
public class ShowController {
    @Autowired
    ShowServiceImpl showServiceImpl;
    @PostMapping("/")
    public ResponseEntity addShow(@RequestBody ShowEntryDTO showEntryDTO){
        ShowResponseDTO show = showServiceImpl.createShow(showEntryDTO);
        return new ResponseEntity<>(new ApiResponse<>("show created successfully",true,new ResponseData<>(show)),HttpStatus.CREATED);

    }
    @PutMapping("/{showId}")
    public ResponseEntity updateShow(@PathVariable int showId,@RequestBody ShowEntryDTO showEntryDTO){
        ShowResponseDTO show = showServiceImpl.updateShow(showEntryDTO,showId);
        return new ResponseEntity<>(new ApiResponse<>("show updated successfully",true,new ResponseData<>(show)),HttpStatus.OK);
    }

    @GetMapping("/movies/{movieId}/theatre/{theatreId}") // lest combine like movie{movieId} or movie - {movieId}
    public ResponseEntity getShowsByMovieAndTheatre(@PathVariable()  int movieId, @PathVariable() @NotNull @Positive int theatreId){
        List<ShowResponseDTO> shows = showServiceImpl.getShowsByMovieAndTheatre(movieId,theatreId);
        return new ResponseEntity<>(new ApiResponse<>("shows retrieved successfully",true,new ResponseData<>(shows)),HttpStatus.OK);
    }

    @GetMapping("/movies/{movieId}/location/{locationName}")
    public ResponseEntity getShowsByLocAndMovie(@PathVariable("locationName") @NotBlank String location,@PathVariable()  int movieId){
        List<ShowResponseDTO> shows = showServiceImpl.getShowsByLocAndMovie(location,movieId);
        return new ResponseEntity<>(new ApiResponse<>("shows retrieved successfully",true,new ResponseData<>(shows)),HttpStatus.OK);
    }

    @GetMapping("/movies/{movieId}")
    public ResponseEntity getShowsByMovie(@PathVariable()  int movieId){
        List<ShowResponseDTO> shows = showServiceImpl.getShowsByMovie(movieId);
        return new ResponseEntity<>(new ApiResponse<>("shows retrieved successfully",true,new ResponseData<>(shows)),HttpStatus.OK);
    }

    //getshows bydate
    @GetMapping("/date") // 2024-04-05 yyyy-MM-dd
    public ResponseEntity getShowsByDate(@RequestParam("date") @NotBlank String date){
        List<ShowResponseDTO> shows = showServiceImpl.getShowsByDate(date);
        return new ResponseEntity<>(new ApiResponse<>("shows retrieved successfully",true,new ResponseData<>(shows)),HttpStatus.OK);
    }

    //getshows by date and movie
    @GetMapping("/movies/{movieId}/date") // 2024-04-05
    public ResponseEntity getShowsByMovieAndDate(@PathVariable() @NotNull int movieId, @RequestParam("date") @NotBlank String date ){
        List<ShowResponseDTO> shows = showServiceImpl.getShowsByMovieAndDate(movieId,date);
        return new ResponseEntity<>(new ApiResponse<>("shows retrieved successfully",true,new ResponseData<>(shows)),HttpStatus.OK);
    }

    @GetMapping("/theatre/{theatreId}")
    public ResponseEntity getShowsByTheatre(@PathVariable() @NotNull @Positive int theatreId ){
        List<ShowResponseDTO> shows = showServiceImpl.getShowsByTheatre(theatreId);
        return new ResponseEntity<>(new ApiResponse<>("shows retrieved successfully",true,new ResponseData<>(shows)),HttpStatus.OK);
    }

    @DeleteMapping("/{showId}")
    public ResponseEntity deleteShowById(@PathVariable int showId){
        String message = showServiceImpl.deleteShowById(showId);
        return new ResponseEntity<>(new ApiResponse<>(message,true),HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity deleteAllShows(@PathVariable int showId){
        String message = showServiceImpl.deleteAll();
        return new ResponseEntity<>(new ApiResponse<>(message,true),HttpStatus.OK);
    }

//    @GetMapping("/GetAvailableSeatsByShowAnd")
}
