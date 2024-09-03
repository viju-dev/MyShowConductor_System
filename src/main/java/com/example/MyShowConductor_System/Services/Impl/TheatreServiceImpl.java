package com.example.MyShowConductor_System.Services.Impl;

import com.example.MyShowConductor_System.Entities.*;
import com.example.MyShowConductor_System.EntryDTOs.TheatreEntryDTO;
import com.example.MyShowConductor_System.Enums.FormatEnum;
import com.example.MyShowConductor_System.Enums.SeatTypeEnum;
import com.example.MyShowConductor_System.Exceptions.ResourceNotFoundException;
import com.example.MyShowConductor_System.Repositories.*;
import com.example.MyShowConductor_System.ResponseDTOs.ShowResponseDTO;
import com.example.MyShowConductor_System.ResponseDTOs.TheatreResponseDTO;
import com.example.MyShowConductor_System.ResponseDTOs.TheatreSeatResponseDto;
import com.example.MyShowConductor_System.ResponseDTOs.TheatreShowsResponseDTO;
import com.example.MyShowConductor_System.Services.TheatreService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TheatreServiceImpl implements TheatreService {

    @Autowired
    TheatreRepository theatreRepository;

    @Autowired
    TheatreSeatRepository theatreSeatRepository;

    @Autowired
    MovieServiceImpl movieServiceImpl;

    @Autowired
    FormatRepository formatRepository;

    @Autowired
    private ModelMapper modelMapper;

    private Logger logger = LoggerFactory.getLogger(TheatreServiceImpl.class);

    @Override // save this constant thig already in database like format and all then just get them by name as its unique attach with enity and save;
    public TheatreResponseDTO createTheatre(TheatreEntryDTO theatreEntryDTO, int classicSeats, int premiumSeats) throws RuntimeException{
        Theatre theatre = this.modelMapper.map(theatreEntryDTO,Theatre.class);
//                System.out.println(formatRepository.findByName(FormatEnum.valueOf("_2D")));
        logger.info(theatre.getName() );
//        theatre.setFormats(new ArrayList<>());//we are eraching not by string but formatenum -> converted to string will give error as in mysql its using varchar in enum form
        List<Format> list  = theatre.getFormats().stream().map(format -> formatRepository.findByName(format.getName()).orElseThrow(() -> new ResourceNotFoundException("formt", "name", format.getName().name()))).collect(Collectors.toList());

        theatre.setFormats(list);
        Theatre savedTheatre = theatreRepository.save(theatre);
//        adding seats
       TheatreResponseDTO result = addSeats(savedTheatre.getId(),classicSeats,premiumSeats);
        return result;
    }

    @Override
    public TheatreResponseDTO updateTheatre(TheatreEntryDTO theatreEntryDTO, int theatreId) {
        Theatre theatre = theatreRepository.findById(theatreId).orElseThrow(()-> new ResourceNotFoundException("theatre","id",Integer.toString(theatreId)));
        theatre.setName(theatreEntryDTO.getName());
        theatre.setAddress(theatreEntryDTO.getAddress());
        theatre.setLocation(theatreEntryDTO.getLocation().toString());//enum to string

        List<Format> list  = theatreEntryDTO.getFormats().stream().map(format -> formatRepository.findByName(format.getName()).orElseThrow(() -> new ResourceNotFoundException("format", "name", format.getName().name()))).collect(Collectors.toList());
        theatre.setFormats(list);
        logger.info("theatre");
        Theatre savedTheatre = theatreRepository.save(theatre);
        return this.modelMapper.map(theatre,TheatreResponseDTO.class);
    }

    @Override
    public TheatreResponseDTO addSeats(int theatreId,int classicSeats,int premiumSeats){

        Theatre theatre = theatreRepository.findById(theatreId).orElseThrow(()->new ResourceNotFoundException("theatre","id",Integer.toString(theatreId)));
        List<TheatreSeat> seatList = new ArrayList<>();

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
        theatre.setTheatreSeatList(seatList);
        Theatre savedTheatre = theatreRepository.save(theatre);
        return this.modelMapper.map(savedTheatre,TheatreResponseDTO.class);
    }

    @Override
    public List<TheatreResponseDTO> getTheatresByFormat(String formatName) {
        Format format = formatRepository.findByName(FormatEnum.valueOf(formatName)).orElseThrow(()-> new ResourceNotFoundException("format","name",formatName));
        List<TheatreResponseDTO> theatres = format.getTheatres().stream().map(theatre -> this.modelMapper.map(theatre,TheatreResponseDTO.class)).collect(Collectors.toList());
        return theatres;
    }

    @Override
    public List<TheatreResponseDTO> getTheatresByLocation(String location) {//i think we should add location table differently
        List<Theatre> theatres = theatreRepository.findAll();
        List<TheatreResponseDTO> theatreList  = theatres.stream().filter(theatre -> theatre.getLocation().toLowerCase().equals(location.toLowerCase())).map(theatre -> this.modelMapper.map(theatre,TheatreResponseDTO.class)).collect(Collectors.toList());
        return theatreList;
    }

    @Override
    public List<TheatreResponseDTO> getAll() {
        List<Theatre> theatres = theatreRepository.findAll();
        List<TheatreResponseDTO> theatreList = theatres.stream().map(theatre -> this.modelMapper.map(theatre,TheatreResponseDTO.class)).collect(Collectors.toList());
        return theatreList;
    }
    @Override
    public List<Theatre> getAllTheatreEntities() {
        return theatreRepository.findAll();
    }

    @Override
    public TheatreResponseDTO getTheatreById(int theatreId) {
        Theatre theatre = theatreRepository.findById(theatreId).orElseThrow(()-> new ResourceNotFoundException("theatre","id",Integer.toString(theatreId)));
        return this.modelMapper.map(theatre,TheatreResponseDTO.class);
    }

    @Override
    public Theatre getTheatreEntityById(int theatreId) {
        return theatreRepository.findById(theatreId).orElseThrow(()-> new ResourceNotFoundException("theatre","id",Integer.toString(theatreId)));
    }

    @Override
    public List<TheatreShowsResponseDTO> getTheatresByLocationAndMovie(String location, int movieId) {
        Movie movie = movieServiceImpl.getMovieEntityById(movieId);
        List<Show> shows = movie.getMovieShowList();
        Set<Theatre> theatres = shows.stream().map(show -> show.getTheatre()).collect(Collectors.toSet());
        List<TheatreShowsResponseDTO> result = theatres.stream().map(theatre -> this.modelMapper.map(theatre,TheatreShowsResponseDTO.class)).collect(Collectors.toList());

        return result;
    }

    @Override
    public List<TheatreResponseDTO> getTheatresByMovie(int movieId) {
        Movie movie = movieServiceImpl.getMovieEntityById(movieId);
        List<Show> shows = movie.getMovieShowList();
        List<TheatreResponseDTO> result = shows.stream().map(show -> show.getTheatre()).map(theatre -> this.modelMapper.map(theatre,TheatreResponseDTO.class)).collect(Collectors.toList());
        return result;
    }

    @Override
    public String delete(int theatreId) {
        Theatre theatre = theatreRepository.findById(theatreId).orElseThrow(()-> new ResourceNotFoundException("theatre","id",Integer.toString(theatreId)));
        theatreRepository.deleteById(theatreId);
        return "Theatre deleted SuccessFully";
    }



    //TheatreShowsResponseDTO
    @Override
    public List<ShowResponseDTO> getShowsByTheatre(int theatreId) {
        Theatre theatre = theatreRepository.findById(theatreId).orElseThrow(()-> new ResourceNotFoundException("theatre","id",Integer.toString(theatreId)));
        List<ShowResponseDTO> shows = theatre.getShowList().stream().map(show -> this.modelMapper.map(show,ShowResponseDTO.class)).collect(Collectors.toList());
        return shows;
    }

    @Override
    public List<TheatreSeatResponseDto> getSeatsByTheatre(int theatreId) {
        Theatre theatre = theatreRepository.findById(theatreId).orElseThrow(()-> new ResourceNotFoundException("theatre","id",Integer.toString(theatreId)));
        List<TheatreSeatResponseDto> seats = theatre.getTheatreSeatList().stream().map(seat -> this.modelMapper.map(seat,TheatreSeatResponseDto.class)).collect(Collectors.toList());
        return seats;
    }


}
