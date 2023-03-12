package com.example.MyShowConductor_System.Services;

import com.example.MyShowConductor_System.Converters.ShowConvertors;
import com.example.MyShowConductor_System.EntryDTOs.ShowEntryDTO;
import com.example.MyShowConductor_System.Enums.SeatTypeEnum;
import com.example.MyShowConductor_System.Entities.*;
import com.example.MyShowConductor_System.Repositories.MovieRepository;
import com.example.MyShowConductor_System.Repositories.ShowRepository;
import com.example.MyShowConductor_System.Repositories.TheatreRepository;
import com.example.MyShowConductor_System.ResponseDTOs.ShowResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShowService {
    @Autowired
    ShowRepository showRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    TheatreRepository theatreRepository;
    public String addShow( ShowEntryDTO showEntryDTO){
        Show show = ShowConvertors.convertEntryToEntity(showEntryDTO);
//        show.setMovie(movieRepository.findById(showEntryDTO.getMovieId()).get());
//        show.setTheatre(theatreRepository.findById(showEntryDTO.getTheatreId()).get());
        Movie movie = movieRepository.findById(showEntryDTO.getMovieId()).get();
        Theatre theatre = theatreRepository.findById(showEntryDTO.getTheatreId()).get();

        //Setting the attribute of foreignKe
        show.setMovie(movie);
        show.setTheatre(theatre);

        //Pending attributes the listOfShowSeatsEnity

        List<ShowSeat> seatList = createShowSeatEntity(showEntryDTO,show);

        show.setShowSeatList(seatList);


        //Now we  also need to update the parent entities


        show = showRepository.save(show);

        movie.getShowList().add(show);
        theatre.getShowList().add(show);


        movieRepository.save(movie);

        theatreRepository.save(theatre);





        return "Show added Successfully";
    }

    public List<ShowResponseDTO> GetShowTime(String movieName, int theatreId) {
        List<ShowResponseDTO> showList = new ArrayList<>();
        int movieId = movieRepository.findByMovieName(movieName).getId();
        for (Show show:showRepository.findAllByMovieIdAndTheatreId(movieId,theatreId)){
            showList.add(ShowConvertors.convertEntityToResponse(show));
        }
        return showList;
    }
    private List<ShowSeat> createShowSeatEntity(ShowEntryDTO showEntryDTO,Show show){



        //Now the goal is to create the ShowSeatEntity
        //We need to set its attribute

        Theatre theatre = show.getTheatre();

        List<TheatreSeat> theatreSeatList = theatre.getTheatreSeatList();

        List<ShowSeat> seatList = new ArrayList<>();

        for(TheatreSeat theatreSeat : theatreSeatList){

            ShowSeat showSeat = new ShowSeat();

            showSeat.setSeatNo(theatreSeat.getSeatNo());
            showSeat.setSeatType(theatreSeat.getType());

            if(theatreSeat.getType().equals(SeatTypeEnum.C))
                showSeat.setPrice(showEntryDTO.getClassicSeatPrice());

            else
                showSeat.setPrice(showEntryDTO.getPremiumSeatPrice());

            showSeat.setBooked(false);
            showSeat.setShow(show); //parent : foreign key for the showSeat Entity

            seatList.add(showSeat); //Adding it to the list
        }

        return  seatList;

    }

    public List<ShowResponseDTO> GetShowTimes(String location, String movieName, String theatreName) {
        List<ShowResponseDTO> showList = new ArrayList<>();
        //check whether theatre with given name exist in given location or not
        Theatre theatre;
        if (!theatreRepository.existsByNameAndLocation(theatreName,location)){
            throw new RuntimeException("Theatre doesn't exist");
        }
        else{
            theatre = theatreRepository.findByNameAndLocation(theatreName,location);
        }
        int theatreId = theatre.getId();
        int movieId = movieRepository.findByMovieName(movieName).getId();
        for (Show show:showRepository.findAllByMovieIdAndTheatreId(movieId,theatreId)){
            showList.add(ShowConvertors.convertEntityToResponse(show));
        }
        return showList;
    }

    public List<ShowResponseDTO> GetShowsByMovie(String movieName) {
        int movieId = movieRepository.findByMovieName(movieName).getId();
        List<ShowResponseDTO> showList = new ArrayList<>();
        for (Show show:showRepository.findAllByMovieId(movieId)){
            showList.add(ShowConvertors.convertEntityToResponse(show));
        }
        return showList;
    }
    public List<ShowResponseDTO> GetShowsByDate(String showDate) {
        LocalDate localDate = LocalDate.parse(showDate);

        List<ShowResponseDTO> showList = new ArrayList<>();
        for (Show show:showRepository.findAllByShowDate(localDate)){//findByAlMovieIdAndShowDate
            ShowResponseDTO showResponseDTO = ShowConvertors.convertEntityToResponse(show);
            showResponseDTO.setMovieName(show.getMovie().getMovieName()); //set moviename
            showResponseDTO.setTheatreName(show.getTheatre().getName());//set theatre name
            showList.add(showResponseDTO);
        }
        return showList;
    }
    public List<ShowResponseDTO> GetShowsByMovieAndDate(String movieName, String showDate) {
        LocalDate localDate = LocalDate.parse(showDate);
        int movieId = movieRepository.findByMovieName(movieName).getId();

        List<ShowResponseDTO> showList = new ArrayList<>();
        for (Show show:showRepository.findAllByMovieIdAndShowDate(movieId,localDate)){//findByAlMovieIdAndShowDate
            ShowResponseDTO showResponseDTO = ShowConvertors.convertEntityToResponse(show);
            showResponseDTO.setMovieName(movieName); //set moviename
            showResponseDTO.setTheatreName(show.getTheatre().getName());//set theatre name
            showList.add(showResponseDTO);
        }
        return showList;
    }


    public List<ShowResponseDTO> GetShowsByTheatreAndMovie(int theatreId, String movieName) {
        int movieId = movieRepository.findByMovieName(movieName).getId();

        List<ShowResponseDTO> showList = new ArrayList<>();
        for (Show show:showRepository.findAllByMovieIdAndTheatreId(movieId,theatreId)){//findByAlMovieIdAndShowDate
            ShowResponseDTO showResponseDTO = ShowConvertors.convertEntityToResponse(show);
            showResponseDTO.setMovieName(movieName); //set moviename
            showResponseDTO.setTheatreName(show.getTheatre().getName());//set theatre name
            showList.add(showResponseDTO);
        }
        return showList;
    }

    public List<ShowResponseDTO> GetShowsByTheatre(int theatreId) {

        List<ShowResponseDTO> showList = new ArrayList<>();
        for (Show show:showRepository.findAllByTheatreId(theatreId)){//findByAlMovieIdAndShowDate
            ShowResponseDTO showResponseDTO = ShowConvertors.convertEntityToResponse(show);
            showResponseDTO.setMovieName(show.getMovie().getMovieName()); //set moviename
            showResponseDTO.setTheatreName(show.getTheatre().getName());//set theatre name
            showList.add(showResponseDTO);
        }
        return showList;
    }
}
