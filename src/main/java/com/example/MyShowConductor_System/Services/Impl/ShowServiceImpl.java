package com.example.MyShowConductor_System.Services.Impl;

import com.example.MyShowConductor_System.Entities.*;
import com.example.MyShowConductor_System.EntryDTOs.ShowEntryDTO;
import com.example.MyShowConductor_System.Enums.SeatTypeEnum;
//import com.example.MyShowConductor_System.Entities.*;
import com.example.MyShowConductor_System.Exceptions.ResourceNotFoundException;
import com.example.MyShowConductor_System.Repositories.FormatRepository;
import com.example.MyShowConductor_System.Repositories.MovieRepository;
import com.example.MyShowConductor_System.Repositories.ShowRepository;
import com.example.MyShowConductor_System.Repositories.TheatreRepository;
import com.example.MyShowConductor_System.ResponseDTOs.ShowResponseDTO;
import com.example.MyShowConductor_System.Services.ShowService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowServiceImpl implements ShowService {
    @Autowired
    ShowRepository showRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    TheatreRepository theatreRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FormatRepository formatRepository;

    private Logger logger = LoggerFactory.getLogger(ShowServiceImpl.class);

    @Transactional
    @Override
    public ShowResponseDTO createShow(ShowEntryDTO showEntryDTO){

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//  Unexpected Exception occur:ModelMapper configuration errors:\r\n\r\n1) The destination property com.example.MyShowConductor_System.Entities.Show.setId() matches multiple source property hierarchies:\n\n\tcom.example.MyShowConductor_System.EntryDTOs.ShowEntryDTO.getMovieId()\n\tcom.example.MyShowConductor_System.EntryDTOs.ShowEntryDTO.getTheatreId()\r\n\r\n1 error
//  doing above or below solve this error
//        modelMapper.getConfiguration().setAmbiguityIgnored(true);
//        modelMapper.typeMap(ShowEntryDTO.class, Show.class).addMappings(mapper -> {
//            mapper.skip(Show::setId);
//        });

        Show show = this.modelMapper.map(showEntryDTO,Show.class);
        show.setFormat(formatRepository.findByName(show.getFormat().getName()).orElseThrow(()-> new ResourceNotFoundException("format","name",show.getFormat().getName().name())));
        Movie movie = movieRepository.findById(showEntryDTO.getMovieId()).orElseThrow(()-> new ResourceNotFoundException("movie","id",Integer.toString(showEntryDTO.getMovieId())));
        Theatre theatre = theatreRepository.findById(showEntryDTO.getTheatreId()).orElseThrow(()-> new ResourceNotFoundException("theatre","id",Integer.toString(showEntryDTO.getTheatreId())));

        //Setting the attribute of foreignKey
//        https://sl.bing.net/fnCfT5BiIsm
        show.setMovie(movie);
        show.setTheatre(theatre);
        Show savedShow = showRepository.save(show);

//        Now we  also need to update the parent entities not mandatory but
//        as it helps to keep the state of the entities in sync with the database within the current persistence context

//        movie.getMovieShowList().add(show);
//        theatre.getShowList().add(show);
//        movieRepository.save(movie);
//        theatreRepository.save(theatre);

        //Pending attributes the listOfShowSeatsEnity
        ShowResponseDTO result = createShowSeats(savedShow.getId(),showEntryDTO.getClassicSeatPrice(),showEntryDTO.getPremiumSeatPrice());

        return result;
    }

    @Override
    public ShowResponseDTO createShowSeats(int showId, int classicPrice, int premiumPrice){
        //Now the goal is to create the ShowSeatEntity
        //We need to set its attribute
        Show show = showRepository.findById(showId).orElseThrow(()-> new ResourceNotFoundException("show","id",Integer.toString(showId)));
        Theatre theatre = show.getTheatre();
        List<TheatreSeat> theatreSeatList = theatre.getTheatreSeatList();

        List<ShowSeat> seatList = theatreSeatList.stream().map(seat-> {
            ShowSeat showSeat = new ShowSeat();
            showSeat.setSeatNo(seat.getSeatNo());
            showSeat.setSeatType(seat.getType());
            if(seat.getType().equals(SeatTypeEnum.C))
                showSeat.setPrice(classicPrice);
            else
                showSeat.setPrice(premiumPrice);
            showSeat.setBooked(false);
            showSeat.setShow(show); //parent : foreign key for the showSeat Entity
// we should save showseat in repo but lets see if it exists
            return showSeat;
        }).collect(Collectors.toList());

        show.setShowSeatList(seatList);
        Show savedShow = showRepository.save(show);
        return  this.modelMapper.map(savedShow,ShowResponseDTO.class);
    }

    @Override
    public ShowResponseDTO getShowById(Integer showId) {
        Show show = showRepository.findById(showId).orElseThrow(()-> new ResourceNotFoundException("show","id",Integer.toString(showId)));
        return this.modelMapper.map(show,ShowResponseDTO.class);
    }

    @Override
    public ShowResponseDTO updateShow(ShowEntryDTO showEntryDTO, int showId) {
        Show show = showRepository.findById(showId).orElseThrow(()-> new ResourceNotFoundException("show","id",Integer.toString(showId)));
        show.setShowDate(showEntryDTO.getShowDate());
        show.setShowTime(showEntryDTO.getShowTime());
//        show.getUpdatedOn()
        show.setFormat(this.modelMapper.map(showEntryDTO.getFormat(),Format.class));
        Show savedShow = showRepository.save(show);
        return this.modelMapper.map(savedShow,ShowResponseDTO.class);
    }

    @Override
    public List<ShowResponseDTO> getAll() {
        List<Show> shows = showRepository.findAll();
        List<ShowResponseDTO>  result = shows.stream().map(show -> this.modelMapper.map(show,ShowResponseDTO.class)).collect(Collectors.toList());
        return result;
    }

    @Override
    public String deleteShowById(Integer showId) {
        Show show = showRepository.findById(showId).orElseThrow(()-> new ResourceNotFoundException("show","id",Integer.toString(showId)));
        showRepository.deleteById(showId);
        return "Show deleted sucessfully"; // can return entity name and attr like resourcenotfound exception
    }

    @Override
    public String deleteAll() {
        showRepository.deleteAll();
        return "Shows deleted sucessfully";
    }

    @Override
    public List<ShowResponseDTO> getShowsByLocAndMovie(String location, int movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(()-> new ResourceNotFoundException("movie","id",Integer.toString(movieId)));
        List<Theatre> theatres = theatreRepository.findAll();
        List<ShowResponseDTO> result = theatres.stream().filter(theatre -> theatre.getLocation().equalsIgnoreCase(location)).flatMap(theatre -> theatre.getShowList().stream()).filter(show -> show.getMovie().equals(movie)).map(show -> this.modelMapper.map(show,ShowResponseDTO.class)).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<ShowResponseDTO> getShowsByMovieAndTheatre(int movieId,int theatreId) {
//        https://sl.bing.net/jw4cdcQk0U8
//        showRepository.findAllByMovieIdAndTheatreId(movieId,theatreId) we can create this named method using query and sometimes  mentioning jpql in repo lets see by performance wise
//          showRepository.findAllByMovieAndTheatre lets see while using this name woks or not
// im might give error if movie or theatre might not be there with that id
//        also we'll try using directly passing entities if we can lets see

        //giving methodNmae was whats exactly getting pass like ehere - findAllByMovieAndTheatre gives error as we are passing ids
        List<Show> shows = showRepository.findAllByMovieIdAndTheatreId(movieId,theatreId);
        List<ShowResponseDTO> result = shows.stream().map(show -> this.modelMapper.map(show,ShowResponseDTO.class)).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<ShowResponseDTO> getShowsByMovie(int movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new ResourceNotFoundException("movie","id",Integer.toString(movieId)));
        List<Show> shows = showRepository.findAll();
        List<ShowResponseDTO> result = shows.stream().filter(show -> show.getMovie().equals(movie)).map(show -> this.modelMapper.map(show,ShowResponseDTO.class)).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<ShowResponseDTO> getShowsByDate(String showDate) {
        List<Show> shows = showRepository.findAll();
        LocalDate localDate = LocalDate.parse(showDate);
        List<ShowResponseDTO> result = shows.stream().filter(show -> show.getShowDate().equals(localDate)).map(show -> this.modelMapper.map(show,ShowResponseDTO.class)).collect(Collectors.toList());
        return result;
    }



    @Override
    public List<ShowResponseDTO> getShowsByMovieAndDate(int movieId, String showDate) {
//        findAllByMovieIdAndShowDate
        LocalDate localDate = LocalDate.parse(showDate);
        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new ResourceNotFoundException("movie","id",Integer.toString(movieId)));
        List<ShowResponseDTO> result = this.showRepository.findAll().stream().filter(show -> show.getShowDate().equals(localDate) && show.getMovie().equals(movie)).map(show -> this.modelMapper.map(show,ShowResponseDTO.class)).collect(Collectors.toList());
        return result;
    }


    @Override
    public List<ShowResponseDTO> getShowsByTheatre(int theatreId) {
        Theatre theatre = theatreRepository.findById(theatreId).orElseThrow(()-> new ResourceNotFoundException("Theatre","id",Integer.toString(theatreId)));
        List<ShowResponseDTO> result = showRepository.findAll().stream().filter(show -> show.getTheatre().equals(theatre)).map(show -> this.modelMapper.map(show,ShowResponseDTO.class)).collect(Collectors.toList());
        return result;
    }

}
