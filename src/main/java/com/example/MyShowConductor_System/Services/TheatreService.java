package com.example.MyShowConductor_System.Services;

import com.example.MyShowConductor_System.Entities.Show;
import com.example.MyShowConductor_System.Entities.Theatre;
import com.example.MyShowConductor_System.Entities.TheatreSeat;
import com.example.MyShowConductor_System.EntryDTOs.TheatreEntryDTO;
import com.example.MyShowConductor_System.ResponseDTOs.ShowResponseDTO;
import com.example.MyShowConductor_System.ResponseDTOs.TheatreResponseDTO;
import com.example.MyShowConductor_System.ResponseDTOs.TheatreSeatResponseDto;
import com.example.MyShowConductor_System.ResponseDTOs.TheatreShowsResponseDTO;
import java.util.List;

public interface TheatreService {
//    basic methods

    public TheatreResponseDTO createTheatre(TheatreEntryDTO theatreEntryDTO,int classicSeats,int premiumSeats);
    public TheatreResponseDTO getTheatreById(int theatreId);

    public TheatreResponseDTO updateTheatre(TheatreEntryDTO theatreEntryDTO, int theatreId);

    public List<TheatreResponseDTO> getAll();

    public String delete(int theatreId);


//    additional methods

    public List<TheatreResponseDTO> getTheatresByLocation(String location);


    public List<TheatreResponseDTO> getTheatresByMovie(int movieId);



    public TheatreResponseDTO addSeats(int theatreId,int classicSeats,int premiumSeats);

    public List<TheatreShowsResponseDTO> getTheatresByLocationAndMovie(String location, int movieId);
    List<TheatreResponseDTO> getTheatresByFormat(String formatName);


//        different methods
//    additional method for absence of attribute

    public List<ShowResponseDTO> getShowsByTheatre(int theatreId); // for all shows
//    acn also create ongoing shows/ movies
//    additional methods for absence of attribute
    List<TheatreSeatResponseDto> getSeatsByTheatre(int theatreId);


}
