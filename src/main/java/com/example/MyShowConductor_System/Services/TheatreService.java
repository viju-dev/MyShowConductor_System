package com.example.MyShowConductor_System.Services;

import com.example.MyShowConductor_System.Converters.TheatreConvertors;
import com.example.MyShowConductor_System.EntryDTOs.TheatreEntryDTO;
import com.example.MyShowConductor_System.Enums.SeatTypeEnum;
import com.example.MyShowConductor_System.Entities.Show;
import com.example.MyShowConductor_System.Entities.Theatre;
import com.example.MyShowConductor_System.Entities.TheatreSeat;
import com.example.MyShowConductor_System.Repositories.MovieRepository;
import com.example.MyShowConductor_System.Repositories.ShowRepository;
import com.example.MyShowConductor_System.Repositories.TheatreRepository;
import com.example.MyShowConductor_System.Repositories.TheatreSeatRepository;
import com.example.MyShowConductor_System.ResponseDTOs.TheatreResponseDTO;
import com.example.MyShowConductor_System.ResponseDTOs.TheatreShowsResponnseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TheatreService {
    @Autowired
    TheatreRepository theatreRepository;

    @Autowired
    TheatreSeatRepository theatreSeatRepository;
    @Autowired
    ShowRepository showRepository;
    @Autowired
    MovieRepository movieRepository;

    public String addTheatre(TheatreEntryDTO theatreEntryDTO) throws RuntimeException{
        Theatre theatre = TheatreConvertors.EntryToEntity(theatreEntryDTO);
        //if theatre with same name and location already exist though an exception
        if (theatreRepository.existsByNameAndLocation(theatre.getName(),theatre.getLocation())){//findByLastnameAndFirstname
            throw new RuntimeException("Theatre with same name and location already exists");
        }
        //get seatslist and set seatlist of theatre
        List<TheatreSeat> seatList = addSeats(theatre,theatreEntryDTO);
        theatre.setTheatreSeatList(seatList);
        //saving theatre
        theatreRepository.save(theatre);
        return "Theatre Successfully added";
    }
    public List<TheatreSeat> addSeats(Theatre theatre,TheatreEntryDTO theatreEntryDTO){
        //taking this two extra attr from theatre Entry Dto
        int classicSeats = theatreEntryDTO.getClassicSeatCount();
        int premiumSeats = theatreEntryDTO.getPremiumSeatCount();

        //get seatslist and set seatlist of theatre
        List<TheatreSeat> seatList = new ArrayList<>();//theatre.getTheatreSeatList(); //was giving error of null arraylist

        //for classic seats
        for (int i=1;i<=classicSeats;i++){
            TheatreSeat theatreSeat = new TheatreSeat();
            theatreSeat.setSeatNo(i+"C");
            theatreSeat.setType(SeatTypeEnum.C);
            theatreSeat.setTheatre(theatre); //optional ig
//            theatreSeatRepository.save(theatreSeat);
            seatList.add(theatreSeat);
            System.out.println(seatList.size());
        }

        //for premium seats
        for (int i=1;i<=premiumSeats;i++){
            TheatreSeat theatreSeat = new TheatreSeat();
            theatreSeat.setSeatNo(i+"P");
            theatreSeat.setType(SeatTypeEnum.P);
            theatreSeat.setTheatre(theatre);
//            theatreSeatRepository.save(theatreSeat);
            seatList.add(theatreSeat);
            System.out.println(seatList.size());
        }
        theatreRepository.save(theatre);
        //not saving child here
        return seatList;
    }




    public List<TheatreShowsResponnseDTO> getAllByLocationAndMovie(String location, String movieName) {
        List<TheatreShowsResponnseDTO> theatreList = new ArrayList<>();
//        for (Theatre theatre:theatreRepository.findByLocationAndMovieName(location,movieName)){
//
//        }
        return theatreList;
    }

    public List<TheatreResponseDTO> getAllByLocation(String location) {
        List<TheatreResponseDTO> theatreList  = new ArrayList<>();
        for (Theatre theatre:theatreRepository.findByLocation(location)){
            theatreList.add(TheatreConvertors.EntityToResponse(theatre));
        }
        return theatreList;
    }

    public List<TheatreResponseDTO> getAll() {
        List<TheatreResponseDTO> theatreList = new ArrayList<>();
        for (Theatre theatre:theatreRepository.findAll()){
            theatreList.add(TheatreConvertors.EntityToResponse(theatre));
        }
        return theatreList;
    }



    public List<TheatreResponseDTO> getAllByMovie(String movieName) {
        List<TheatreResponseDTO> theatreList = new ArrayList<>();
        List<Integer> theatreIds = new ArrayList<>();
        int movieId = movieRepository.findByTitle(movieName).getId();
        for (Show show:showRepository.findAllByMovieId(movieId)){
            if (!theatreIds.contains(show.getTheatre().getId())){
                theatreList.add(TheatreConvertors.EntityToResponse(show.getTheatre()));
            }
        }
        return theatreList;
    }

    public String deleteById(int theatreId) {
        theatreRepository.deleteById(theatreId);
        return "Theatre deleted SuccessFully";
    }
}
