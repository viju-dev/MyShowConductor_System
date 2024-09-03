package com.example.MyShowConductor_System.Controllers;

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
import com.example.MyShowConductor_System.Services.Impl.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceImplTest2 {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ShowRepository showRepository;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private FormatRepository formatRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private LanguageRepository languageRepository;

    @InjectMocks
    private MovieServiceImpl movieServiceImpl;

    private MovieEntryDTO movieEntryDTO;
    private Movie movie;

    @BeforeEach
    public void setUp() {

        GenreEntryDto genreEntryDto = new GenreEntryDto();
        genreEntryDto.setName(MovieGenreEnum.ACTION);
        FormatEntryDto formatEntryDto = new FormatEntryDto();
        formatEntryDto.setName(FormatEnum._2D);
        LanguageEntryDto languageEntryDto = new LanguageEntryDto();
        languageEntryDto.setName(LanguagesEnum.ENGLISH);
        movieEntryDTO = new MovieEntryDTO();
        movieEntryDTO.setTitle("Test Movie");
        movieEntryDTO.setGenres(Arrays.asList(genreEntryDto));
        movieEntryDTO.setFormats(Arrays.asList(formatEntryDto));
        movieEntryDTO.setDuration(120);
        movieEntryDTO.setLanguages(Arrays.asList(languageEntryDto));

        Genre genre = new Genre();
        genre.setName(MovieGenreEnum.ACTION);
        Genre savedGenre = genreRepository.save(genre);

        Format format = new Format();
        format.setName(FormatEnum._2D);
        Format savedFormat = formatRepository.save(format);

        Language language = new Language();
        movie.setId(1);
        movie.setTitle("Test Movie");
        movie.setGenres(Arrays.asList(genre));
        movie.setFormats(Arrays.asList(format));
        movie.setDuration(120);
        movie.setLanguages(Arrays.asList(language));
    }

    @Test
    public void testCreateMovieWithValidInput() {
        // Arrange
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);
        when(modelMapper.map(any(MovieEntryDTO.class), eq(Movie.class))).thenReturn(movie);
        when(modelMapper.map(any(Movie.class), eq(MovieResponseDTO.class))).thenReturn(new MovieResponseDTO());

        // Act
        MovieResponseDTO movieResponseDTO = movieServiceImpl.createMovie(movieEntryDTO);

        // Assert
        assertNotNull(movieResponseDTO);
        assertEquals(movieEntryDTO.getTitle(), movieResponseDTO.getTitle());
    }

//    @Test
//    public void testCreateMovieWithInvalidGenre() {
//        // Arrange
//        movieEntryDTO.setGenres(Collections.singletonList(new GenreEntryDto("InvalidGenre")));
//        when(genreRepository.findByName(anyString())).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(ResourceNotFoundException.class, () -> {
//            movieServiceImpl.createMovie(movieEntryDTO);
//        });
//    }

    @Test
    public void testUpdateMovieWithValidInput() {
        // Arrange
        int movieId = 1;
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);
        when(modelMapper.map(any(Movie.class), eq(MovieResponseDTO.class))).thenReturn(new MovieResponseDTO());

        // Act
        MovieResponseDTO movieResponseDTO = movieServiceImpl.updateMovie(movieEntryDTO, movieId);

        // Assert
        assertNotNull(movieResponseDTO);
        assertEquals(movieEntryDTO.getTitle(), movieResponseDTO.getTitle());
    }

    @Test
    public void testGetMovieByValidId() {
        // Arrange
        int movieId = 1;
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(modelMapper.map(any(Movie.class), eq(MovieResponseDTO.class))).thenReturn(new MovieResponseDTO());

        // Act
        MovieResponseDTO movieResponseDTO = movieServiceImpl.getMovieById(movieId);

        // Assert
        assertNotNull(movieResponseDTO);
        assertEquals(movie.getTitle(), movieResponseDTO.getTitle());
    }

    @Test
    public void testDeleteMovieByValidId() {
        // Arrange
        int movieId = 1;
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        // Act
        movieServiceImpl.deleteMovieById(movieId);

        // Assert
        verify(movieRepository, times(1)).delete(movie);
    }
}
