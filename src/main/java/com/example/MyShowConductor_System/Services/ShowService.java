package com.example.MyShowConductor_System.Services;

import com.example.MyShowConductor_System.Entities.Show;
import com.example.MyShowConductor_System.EntryDTOs.ShowEntryDTO;
import com.example.MyShowConductor_System.ResponseDTOs.ShowResponseDTO;

import java.util.List;

public interface ShowService {
//    basic methods
    public ShowResponseDTO createShow(ShowEntryDTO showEntryDTO);
    public ShowResponseDTO getShowById(Integer showId);
    public Show getShowEntityById(Integer showId);
    public ShowResponseDTO updateShow(ShowEntryDTO showEntryDTO,int showId);
    public List<ShowResponseDTO> getAll();
    public String deleteShowById(Integer showId);
    public String deleteAll();


//    additional methods
    public List<ShowResponseDTO> getShowsByLocAndMovie(String location, int movieId);//or name
    //getShowsByMovieAndTheatre but how can we as theare would have location already and we are passing theatre

    public List<ShowResponseDTO> getShowsByMovieAndTheatre(int theatreId, int movieId);

    //    public List<ShowResponseDTO> getShowsByTheatre(int theatreId);
    public List<ShowResponseDTO> getShowsByMovie (int movieId) ;

    public List<ShowResponseDTO> getShowsByDate(String showDate);

    public List<ShowResponseDTO> getShowsByMovieAndDate(int movieId, String showDate);


//  different methods
    public ShowResponseDTO createShowSeats(int showId,int classicPrice, int premiumPrice);


//    additional method for absence of attribute

     List<ShowResponseDTO> getShowsByTheatre(int theatreId);

//

}
