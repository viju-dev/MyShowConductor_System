package com.example.MyShowConductor_System.Controllers;

import com.example.MyShowConductor_System.EntryDTOs.TheatreEntryDTO;
import com.example.MyShowConductor_System.Payloads.ApiResponse;
import com.example.MyShowConductor_System.Payloads.ResponseData;
import com.example.MyShowConductor_System.ResponseDTOs.TheatreResponseDTO;
import com.example.MyShowConductor_System.ResponseDTOs.TheatreShowsResponseDTO;
import com.example.MyShowConductor_System.Services.Impl.TheatreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Validated
@RequestMapping(value = "/api/theatres",produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class TheatreController {
    @Autowired
    TheatreServiceImpl theatreServiceImpl;

    @PostMapping(value = "/")
    public ResponseEntity createTheatre(@RequestBody TheatreEntryDTO theatreEntryDTO,@RequestParam int classicSeats,@RequestParam int premiumSeats){
        TheatreResponseDTO theatre = theatreServiceImpl.createTheatre(theatreEntryDTO,classicSeats,premiumSeats);
        return new ResponseEntity<>(new ApiResponse<>("Theatre added successfully",true,new ResponseData(theatre)),HttpStatus.CREATED);
    }

    @PutMapping("/{theatreId}")
    public ResponseEntity updateTheatre(@RequestBody TheatreEntryDTO theatreEntryDTO, @PathVariable int theatreId){
        TheatreResponseDTO theatre = theatreServiceImpl.updateTheatre(theatreEntryDTO,theatreId);
        return new ResponseEntity<>(new ApiResponse<>("Theatre updated successfully",true,new ResponseData(theatre)),HttpStatus.CREATED);
    }
    @PutMapping("/{theatreId}/seats")
    public ResponseEntity updateTheatreSeats(@PathVariable int theatreId, @RequestParam int classicSeats,@RequestParam int premiumSeats){
        TheatreResponseDTO theatre = theatreServiceImpl.addSeats(theatreId,classicSeats,premiumSeats);
        return new ResponseEntity<>(new ApiResponse<>("Theatre seats updated successfully",true,new ResponseData(theatre)),HttpStatus.CREATED);
    }


    //getTheatres with shows by location and movie
    @GetMapping("/movie/{movieId}/location/{location}")
    public ResponseEntity getAllByLocationAndMovie(@PathVariable("location") @NotBlank String location, @PathVariable("movieName") @NotBlank int movieId){
        List<TheatreShowsResponseDTO> theatreList = theatreServiceImpl.getTheatresByLocationAndMovie(location,movieId);
        return new ResponseEntity<>(new ApiResponse<>("Theatre retrieved successfully",true,new ResponseData(theatreList)),HttpStatus.OK);
    }

    //getAll theatres by location
    @GetMapping("/location/{location}")
    public ResponseEntity getAllByLocation(@PathVariable("location") @NotBlank String location){
        List<TheatreResponseDTO> theatreList = theatreServiceImpl.getTheatresByLocation(location);
        return new ResponseEntity<>(new ApiResponse<>("Theatre retrieved successfully",true,new ResponseData(theatreList)),HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity getAll(){
        List<TheatreResponseDTO> theatreList = theatreServiceImpl.getAll();
        return new ResponseEntity<>(new ApiResponse<>("Theatre retrieved successfully",true,new ResponseData(theatreList)),HttpStatus.OK);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity getAllByMovie(@PathVariable("movieName") @NotBlank int movieId){
        List<TheatreResponseDTO> theatreList = theatreServiceImpl.getTheatresByMovie(movieId);
        return new ResponseEntity<>(new ApiResponse<>("Theatre retrieved successfully",true,new ResponseData(theatreList)),HttpStatus.OK);
    }
    // by id
    @GetMapping("/{theatreId}")
    public ResponseEntity getTheatreById(@PathVariable("theatreId")  int theatreId){
        TheatreResponseDTO theatre = theatreServiceImpl.getTheatreById(theatreId);
        return new ResponseEntity<>(new ApiResponse<>("Theatre retrieved successfully",true,new ResponseData(theatre)),HttpStatus.OK);
    }
    //by format
    @GetMapping("/format/{formatName}")
    public ResponseEntity getTheatreByFormat(@RequestParam("formatName") @NotBlank String formatName){
        List<TheatreResponseDTO> theatreList = theatreServiceImpl.getTheatresByFormat(formatName);
        return new ResponseEntity<>(new ApiResponse<>("Theatre retrieved successfully",true,new ResponseData(theatreList)),HttpStatus.OK);
    }

    @DeleteMapping("/{theatreId}")
    public ResponseEntity deleteById(@RequestParam("theatreId") @NotNull @Positive int theatreId){
        String result = theatreServiceImpl.delete(theatreId);
        return new ResponseEntity<>(new ApiResponse<>("Theatre deleted successfully",true),HttpStatus.OK);
    }

}
