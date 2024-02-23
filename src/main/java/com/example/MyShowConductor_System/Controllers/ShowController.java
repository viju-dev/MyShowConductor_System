package com.example.MyShowConductor_System.Controllers;

import com.example.MyShowConductor_System.EntryDTOs.ShowEntryDTO;
import com.example.MyShowConductor_System.ResponseDTOs.ShowResponseDTO;
import com.example.MyShowConductor_System.Services.ShowService;
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

@RestController
@Validated
@RequestMapping("/shows")
public class ShowController {
    @Autowired
    ShowService showService;
    @PostMapping("/add")
    public ResponseEntity addShow(@RequestBody ShowEntryDTO showEntryDTO){
        try {
            String result = showService.addShow(showEntryDTO);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        catch (Exception e){
            String response = "Show Not Added";
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }
    //get SHOWtimes by moviename,thetarename
    @GetMapping("/showtime-by-movie-and-theatre")
    public ResponseEntity getShowTime(@RequestParam("movieName") @NotBlank String movieName, @RequestParam("theatreId") @NotNull @Positive int theatreId){
        List<ShowResponseDTO> showList = showService.getShowTime(movieName,theatreId);
        return new ResponseEntity<>(showList,HttpStatus.OK);
    }
    @GetMapping("/showtime-by-location-movie-theatre")
    public ResponseEntity getShowTimes(@RequestParam("location") @NotBlank String location,@RequestParam("movieName") @NotBlank String movieName,@RequestParam("theatreName") @NotBlank String theatreName){
        List<ShowResponseDTO> showList = showService.getShowTimes(location,movieName,theatreName);

        return new ResponseEntity(showList,HttpStatus.OK);
    }

    @GetMapping("/by-movie")
    public ResponseEntity getShowsByMovie(@RequestParam("movieName") @NotBlank String movieName){
        List<ShowResponseDTO> showList = showService.getShowsByMovie(movieName);
        return new ResponseEntity<>(showList,HttpStatus.OK);
    }
    //getshows bydate
    @GetMapping("/by-date")
    public ResponseEntity getShowsByDate(@RequestParam("date") @NotBlank String date){
        List<ShowResponseDTO> showList = showService.getShowsByDate(date);
        return new ResponseEntity<>(showList,HttpStatus.OK);
    }
    //getshows by date and movie
    @GetMapping("/by-movie-and-date")
    public ResponseEntity getShowsByMovieAndDate(@RequestParam("movieName") @NotBlank String movieName, @RequestParam("date") @NotBlank String date ){
        List<ShowResponseDTO> showList = showService.getShowsByMovieAndDate(movieName,date);
        return new ResponseEntity<>(showList,HttpStatus.OK);
    }
    @GetMapping("/by-theatre-and-movie")
    public ResponseEntity getShowsByTheatreAndMovie(@RequestParam("theatreId") @NotNull @Positive int theatreId, @RequestParam("movieName") @NotBlank String movieName ){
        List<ShowResponseDTO> showList = showService.getShowsByTheatreAndMovie(theatreId,movieName);
        return new ResponseEntity<>(showList,HttpStatus.OK);
    }
    @GetMapping("/by-theatre")
    public ResponseEntity getShowsByTheatre(@RequestParam("theatreId") @NotNull @Positive int theatreId ){
        List<ShowResponseDTO> showList = showService.getShowsByTheatre(theatreId);
        return new ResponseEntity<>(showList,HttpStatus.OK);
    }

//    @GetMapping("/GetAvailableSeatsByShowAnd")
}
