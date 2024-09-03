package com.example.MyShowConductor_System.Services.Impl;

import com.example.MyShowConductor_System.Entities.*;
import com.example.MyShowConductor_System.EntryDTOs.FormatEntryDto;
import com.example.MyShowConductor_System.EntryDTOs.GenreEntryDto;
import com.example.MyShowConductor_System.EntryDTOs.LanguageEntryDto;
import com.example.MyShowConductor_System.EntryDTOs.MovieEntryDTO;
import com.example.MyShowConductor_System.Enums.FormatEnum;
import com.example.MyShowConductor_System.Enums.LanguagesEnum;
import com.example.MyShowConductor_System.Enums.MovieGenreEnum;
import com.example.MyShowConductor_System.Exceptions.ResourceNotFoundException;
import com.example.MyShowConductor_System.Repositories.*;
import com.example.MyShowConductor_System.ResponseDTOs.MovieResponseDTO;
import com.example.MyShowConductor_System.Services.MovieService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    ShowRepository showRepository;
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FormatRepository formatRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private LanguageRepository languageRepository;

    private Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Override
    public MovieResponseDTO createMovie(MovieEntryDTO movieEntryDTO)  {//throws Exception
        Movie movie = this.modelMapper.map(movieEntryDTO,Movie.class);
        movie.setFormats(movie.getFormats().stream().map(format -> formatRepository.findByName(format.getName()).orElseThrow(()->new ResourceNotFoundException("format","name",format.getName().name()))).collect(Collectors.toList()));
        movie.setGenres(movie.getGenres().stream().map(genre -> genreRepository.findByName(genre.getName()).orElseThrow(()->new ResourceNotFoundException("genre","name",genre.getName().name()))).collect(Collectors.toList()));
        movie.setLanguages(movie.getLanguages().stream().map(language -> languageRepository.findByName(language.getName()).orElseThrow(()->new ResourceNotFoundException("language","name",language.getName().name()))).collect(Collectors.toList()));
        logger.info("movie printed - ",movie);
//        System.out.println(movie);
        Movie savedMovie = movieRepository.save(movie);
        return this.modelMapper.map(savedMovie,MovieResponseDTO.class);
    }

//    What is the advantage of optional?
//With Optional, you can write cleaner and more concise code that is easier to maintain. One of the key benefits of using Optional is that it forces you to handle the case where the value is absent. This means that you are less likely to miss important checks in your code and reduces the risk of NullPointerException.7 Feb 2023
    @Override
    public MovieResponseDTO getMovieById(int id){
        Movie movie =  movieRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("movie","id",Integer.toString(id)));
        return this.modelMapper.map(movie,MovieResponseDTO.class);
    }

    @Override
    public Movie getMovieEntityById(int id){
        return movieRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("movie","id",Integer.toString(id)));
    }


    @Override
    public List<MovieResponseDTO> getMoviesByName(String name){ // ad method name.contains(animal) spo it can show animal 2 as well
        // diff types of query methods
        List<Movie> movies = movieRepository.findByTitle(name); //what if i use stream on emty list would it thow error ?
        List<MovieResponseDTO> responses = movies.stream().map(movie -> this.modelMapper.map(movie, MovieResponseDTO.class)).collect(Collectors.toList());
        return responses;
    }

    @Override
    public List<MovieResponseDTO> getMoviesByLanguages(List<LanguagesEnum> languages){
        //  return movieRepository.findByLanguages(languages); //Collections.singletonList(languages) //used to convert string to array

        List<Movie> movies = new ArrayList<>();
//        Set<MovieResponseDTO> result = new TreeSet<>();
        for (LanguagesEnum lang : languages) {
            movies.addAll(movieRepository.findByLanguagesName(lang));
        }
//        languages.stream().map(lang -> movieRepository.findByLanguagesName(lang.getName()).isPresent()).anyMatch(found -> !found)
        List<MovieResponseDTO> result = movies.stream().map(movie-> this.modelMapper.map(movie,MovieResponseDTO.class)).collect(Collectors.toList());
        return result;
//        return new ArrayList<>(result); // Convert the Set back to a List
    }


    @Override
    public List<MovieResponseDTO> getMoviesByGenres(List<MovieGenreEnum> genres){ //list of enums?

        List<Movie> movies = new ArrayList<>();
//        Set<MovieResponseDTO> result = new TreeSet<>();
        for (MovieGenreEnum genre : genres) {
//            MovieGenreEnum gen = genre.getName();
            movies.addAll(movieRepository.findByGenresName(genre));
        }
        List<MovieResponseDTO> result = movies.stream().map(movie-> this.modelMapper.map(movie,MovieResponseDTO.class)).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<MovieResponseDTO> getMoviesByFormats( List<FormatEnum> formats){
        List<Movie> movies = new ArrayList<>();
//        Set<MovieResponseDTO> result = new TreeSet<>();
        for (FormatEnum format : formats) {
            movies.addAll(movieRepository.findByFormatsName(format));
        }
        List<MovieResponseDTO> result = movies.stream().map(movie-> this.modelMapper.map(movie,MovieResponseDTO.class)).collect(Collectors.toList());
        return result;
    }
//    @Override
//    public List<MovieResponseDTO> getMoviesByFormats( List<FormatEntryDto> formats){
//        List<Movie> movies = new ArrayList<>();
////        Set<MovieResponseDTO> result = new TreeSet<>();
//        for (FormatEntryDto format : formats) {
//            movies.addAll(movieRepository.findByFormatsName(format.getName()));
//        }
//        List<MovieResponseDTO> result = movies.stream().map(movie-> this.modelMapper.map(movie,MovieResponseDTO.class)).collect(Collectors.toList());
//        return result;
//    }

    @Override
    public List<MovieResponseDTO> getAll(){
        List<Movie> movies = movieRepository.findAll();
        Set<MovieResponseDTO> result = movies
                                        .stream().map(movie -> this.modelMapper
                                        .map(movie,MovieResponseDTO.class))
                                        .collect(Collectors.toSet());
        return new ArrayList<>(result);
    }

    @Override
    public List<MovieResponseDTO> getTopMovies() {
        List<MovieResponseDTO> movies = new ArrayList<>();//movieRepository.findbyRatingTop()
        return movies;
    }

    @Override // we'll do it later based on ratinr or custom sql top5/10 sorted by descending rating from sql entruies
    public List<MovieResponseDTO> getMovieByMaxShows() { // one more method by rating
//        int movieId = showRepository.getMovieByMax();
//        String movieName=movieRepository.findById(movieId).get().getTitle();
        return new ArrayList<MovieResponseDTO>();
    }

    @Override
    public MovieResponseDTO updateMovie(MovieEntryDTO movieEntryDTO, int movieId){
        Movie movie = movieRepository.findById(movieId).orElseThrow(()-> new ResourceNotFoundException("movie","id",Integer.toString(movieId)));
        movie.setTitle(movieEntryDTO.getTitle());
//        movie.setRating(movieEntryDTO.());
        movie.setLanguages(movieEntryDTO.getLanguages().stream().map(language -> languageRepository.findByName(language.getName()).orElseThrow(()->new ResourceNotFoundException("language","name",language.getName().name()))).collect(Collectors.toList()));
        movie.setDuration(movieEntryDTO.getDuration());
        movie.setFormats(movieEntryDTO.getFormats().stream().map(format-> formatRepository.findByName(format.getName()).orElseThrow(()->new ResourceNotFoundException("format","name",format.getName().name()))).collect(Collectors.toList()));

        Movie savedMovie = movieRepository.save(movie);
        return this.modelMapper.map(savedMovie,MovieResponseDTO.class);

    }

    @Override
    public String deleteMovieById(int id){
        Movie movie = movieRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("movie","id",Integer.toString(id)));
        movieRepository.deleteById(id);
        return "Movie Deleted SuccessFully";
    }

    @Override
    public String deleteAll(){
        movieRepository.deleteAll();
        return "All Movies Are Deleted successFully";
    }


    @Override
    public long getCollectionByMovie(int movieId) {
        
        long total = 0;
//        for (Show show:showRepository.findAllByMovieId(movieId)){//iterate on show
//            for (ShowSeat showSeat:show.getShowSeatList()){//showSeatList by show
//                if (showSeat.isBooked()){
//                    total += showSeat.getPrice();//checked whether ticket were book or not  if its add price in total
//                }
//            }
//        }
        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new ResourceNotFoundException("movie","id",Integer.toString(movieId)));
        List<Show> showList = movie.getMovieShowList();
         total = showList.stream()
                .flatMap(show -> show.getShowSeatList().stream())
                .mapToLong(showSeat -> showSeat.getPrice())
                 .sum();

        return total;
    }

    public void sendMail(String email,String text,String subject) throws MessagingException {
        //  String body = "Hi this is to confirm your booking for seat No "+allotedSeats +"for the movie : " + ticketEntity.getMovieName();


        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom("from@gmail.com");
        mimeMessageHelper.setTo("sentoTo@gmail.com");
//        mimeMessageHelper.setText("you got my message");
        mimeMessageHelper.setText("my text <img src='cid:myLogo'>", true);
        mimeMessageHelper.setSubject("Confirming your booked Ticket");

        javaMailSender.send(mimeMessage);
    }



}
