package com.example.MyShowConductor_System.Services;

import com.example.MyShowConductor_System.Converters.MovieConvertors;
import com.example.MyShowConductor_System.EntryDTOs.MovieEntryDTO;
import com.example.MyShowConductor_System.Entities.Movie;
import com.example.MyShowConductor_System.Entities.Show;
import com.example.MyShowConductor_System.Entities.ShowSeat;
import com.example.MyShowConductor_System.Repositories.MovieRepository;
import com.example.MyShowConductor_System.Repositories.ShowRepository;
import com.example.MyShowConductor_System.ResponseDTOs.MovieResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    ShowRepository showRepository;
    @Autowired
    JavaMailSender javaMailSender;

    public String addMovie(MovieEntryDTO movieEntryDTO) throws Exception {
        Movie movie = MovieConvertors.convertEntryDtoToEntity(movieEntryDTO);
        movieRepository.save(movie);

        return "Movie Added Successfully;";
    }

    public MovieResponseDTO getById(int id){
        return MovieConvertors.convertDtoToResponse(movieRepository.findById(id).get());
    }

    public MovieResponseDTO getByName(String name){
        return MovieConvertors.convertDtoToResponse(movieRepository.findByTitle(name));
    }

    public List<MovieResponseDTO> getByLanguages(String languages){
      //  return movieRepository.findByLanguages(languages); //Collections.singletonList(languages) //used to convert string to array

        String[] language = languages.split(",");
        List<Movie> movieList = movieRepository.findAll();
        List<MovieResponseDTO> ans = new ArrayList<>();
        for (Movie movie:movieList){
            for (String lang:language){
                String movieLang = movie.getLanguages();
                if (movieLang.contains(lang)){
                    ans.add(MovieConvertors.convertDtoToResponse(movie));
                }
            }
        }
        return ans;
    }

    public List<MovieResponseDTO> getByGenre(String genres){ //list of enums?
        String[] genre = genres.split(",");
        List<Movie> movieList = movieRepository.findAll();
        List<MovieResponseDTO> ans = new ArrayList<>();
        for (Movie movie:movieList){
            for (String genr:genre){
                String movieGenre = movie.getGenres();
                if (movieGenre.contains(genr)){
                    ans.add(MovieConvertors.convertDtoToResponse(movie));
                }
            }
        }
        return ans;
    }

//    public ResponseEntity getByFormat( String screenType){
//        return new ResponseEntity<>(HttpStatus.FOUND);
//    }

    public List<MovieResponseDTO> getAll(){
        List<MovieResponseDTO> movieResponseDTOList = new ArrayList<>();
        for (Movie movie: movieRepository.findAll()){
            movieResponseDTOList.add( MovieConvertors.convertDtoToResponse(movie));
        }
        return movieResponseDTOList;
    }
    public Movie getTopMovie() {
        Movie movie = new Movie();
        return movie;
    }
    public String getByMaxShows() {
        int movieId = showRepository.getMovieByMax();
        String movieName=movieRepository.findById(movieId).get().getTitle();
        return movieName;
    }
    public String editMovie(@RequestBody MovieEntryDTO movieEntryDTO){
        movieRepository.save(MovieConvertors.convertEntryDtoToEntity(movieEntryDTO));
        return "Movie Updated Successfully";
    }
    public String deleteById(int id){
        movieRepository.deleteById(id);
        return "Movie Deleted SuccessFully";
    }
    public String deleteByName(String name){
        movieRepository.deleteByTitle(name);
        return "Movie Deleted SuccessFully";
    }
    public String deleteAll(){
        movieRepository.deleteAll();
        return "All Movies Are Deleted successFully";
    }


    public long getCollectionByMovie(String movieName) {
        int movieId = movieRepository.findByTitle(movieName).getId();
        long total = 0;
        for (Show show:showRepository.findAllByMovieId(movieId)){//iterate on show
            for (ShowSeat showSeat:show.getShowSeatList()){//showSeatList by show
                if (showSeat.isBooked()){
                    total += showSeat.getPrice();//checked whether ticket were book or not  if its add price in total
                }
            }
        }
        return total;
    }

    public void sendMail(String email,String text,String subject) throws MessagingException {
        //  String body = "Hi this is to confirm your booking for seat No "+allotedSeats +"for the movie : " + ticketEntity.getMovieName();


        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom("myshowconductor@gmail.com");
        mimeMessageHelper.setTo("demonslayergaming9@gmail.com");
//        mimeMessageHelper.setText("you got my message");
        mimeMessageHelper.setText("my text <img src='cid:myLogo'>", true);
        mimeMessageHelper.setSubject("Confirming your booked Ticket");

        javaMailSender.send(mimeMessage);
    }


}
